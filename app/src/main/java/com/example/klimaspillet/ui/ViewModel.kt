@file:Suppress("SpellCheckingInspection")

package com.example.klimaspillet.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.klimaspillet.data.repository.CO2TingListe
import com.example.klimaspillet.data.models.CO2Ting
import com.example.klimaspillet.data.repository.CO2ItemsRepository
import com.example.klimaspillet.navigation.Routes
import kotlinx.coroutines.launch
import com.example.klimaspillet.data.models.GameUiState
import com.example.klimaspillet.data.models.Student
import com.example.klimaspillet.data.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import java.net.HttpURLConnection
import java.net.URL

//   ------------------------------------
//   Hovedsageligt ansvarlig: Victor Lotz
//   ------------------------------------

class ViewModel : ViewModel() {
    val CO2Itemrepository = CO2ItemsRepository()
    val studentRepository = StudentRepository()

    // Game UI state
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // Image state
    private var _imageMap = MutableStateFlow<Map<String, Bitmap>>(emptyMap())
    var imageMap: StateFlow<Map<String, Bitmap>> = _imageMap

    // Liste af allerede brugte CO2-ting i spillet.
    private var usedCO2Things: MutableSet<CO2Ting> = mutableSetOf()

    // Altid ny highscore, hvis highscore er 0 (eller er ny bruger og derfor har 0).
    // Ellers newHighscore default false.
    var newHighscoreBoolean = false
    var finalScore = 0;

    var connectedStudent = ""
    var connectedClassCode = ""

    private val topStudentsList = MutableLiveData<List<Student>>()

    val topStudents: LiveData<List<Student>> get() = topStudentsList

    private val _className = MutableLiveData<String>()

    val className: LiveData<String> get() = _className

    //AndreasRG:
    // Henter talet fra crownMoverFunction
    val numberCrownMover: Int
        get() = uiState.value.crownMoverValue

    fun addStudent(name: String, classCode: String, emoji: String) {
        viewModelScope.launch {
            if (!studentRepository.getClassByClassCode(classCode).isEmpty()) {
                // Tilføjer ny student med allerede eksisterende highscore og connecter til klasse.
                connectedStudent = studentRepository.newStudent(name, classCode, emoji, uiState.value.highscore)
            } else {
                println("Klassekode eksisterer ikke")
            }
        }
    }

    fun retriveTop3Students(classCode: String) {
        viewModelScope.launch {
            val studentList = studentRepository.getStudentsFromClass(classCode)
            val sortedStudents = studentList.sortedByDescending { it.highscore }.take(3)

            println("Retrieved students: ${sortedStudents.map { it.name }}") // Debugging
            topStudentsList.postValue(sortedStudents)
        }
    }

    fun fetchClassName(classCode: String) {
        viewModelScope.launch {
            val name = studentRepository.getClassNameByClassCode(classCode) // Call repository function
            _className.postValue(name ?: "Unknown Class") // Safely update LiveData
        }
    }

    //AndreasRG:
    // Giver et tal som gør der bliver brugt en speciel form for layout på kronen ved ny highscore
    fun crownMoverFunction(currentScore: Int) {
        _uiState.value = _uiState.value.copy(
            crownMoverValue = when {
                currentScore in 0..4 -> 0
                currentScore in 5..9 -> 1
                currentScore in 10..100 -> 2
                else -> _uiState.value.crownMoverValue
            }
        )
    }

    /*
    private fun pickRandomThingAndShuffle(): CO2Ting {
        // Vælg en ny tilfældig CO2 ting indtil der findes én som ikke er brugt tidligere.
        val randomCO2Ting = CO2TingListe.random()
        // Hvis man ender med at bruge alle CO2 ting, bliver usedCO2Things cleared, så man kan spille videre.
        if(usedCO2Things.size == CO2TingListe.size) {
            usedCO2Things.clear()
        }
        if (usedCO2Things.contains(randomCO2Ting)) {
            return pickRandomThingAndShuffle()
        } else {
            usedCO2Things.add(randomCO2Ting)
            return randomCO2Ting
        }
    }*/

    var CO2ItemIndex = 0
    private fun pickNextRandomCO2Ting(): CO2Ting {
        if(CO2ItemIndex == CO2TingListe.size) {
            CO2TingListe.shuffle()
            CO2ItemIndex = 0
        }
        val nextRandomCO2Ting = CO2TingListe[CO2ItemIndex];
        CO2ItemIndex++;
        return nextRandomCO2Ting;
    }

    // Man kunne også lave "chooseOption(color: String)", men det ved jeg ikke om bliver forvirrende?
    fun chooseRedOption(navController: NavController) {
        if(uiState.value.currentRedOption.CO2 >= uiState.value.currentYellowOption.CO2) {
            nextQuestion()
        } else {
            endGame(navController)
        }
    }

    fun chooseYellowOption(navController: NavController) {
        if(uiState.value.currentRedOption.CO2 <= uiState.value.currentYellowOption.CO2) {
            nextQuestion()
        } else {
            endGame(navController)
        }
    }

    fun endGame(navController: NavController) {
        if(newHighscoreBoolean && connectedStudent != "") {
            studentRepository.updateHighscore(connectedStudent, uiState.value.highscore)
        }
        finalScore = uiState.value.score;
        navController.navigate(Routes.routeResultsScreen)
    }

    fun nextQuestion() {
        var newScore = uiState.value.score+1
        var newHighscore: Int

        if(newScore > uiState.value.highscore) {
            newHighscore = newScore
            newHighscoreBoolean = true
        } else {
            newHighscore = uiState.value.highscore
        }

        // Gul option bliver rød, rød bliver til næste rød og næste rød kalder pickRandom.
        _uiState.value = GameUiState(
            playerID = uiState.value.playerID,
            highscore = newHighscore,
            score = newScore,
            currentYellowOption = uiState.value.currentRedOption,
            currentRedOption = uiState.value.nextRedOption,
            nextRedOption = pickNextRandomCO2Ting()
        )
        preloadImage(uiState.value.nextRedOption.image)
    }

    fun resetGame() {
        // Clear imageMap
        _imageMap = MutableStateFlow<Map<String, Bitmap>>(emptyMap())
        imageMap = _imageMap

        newHighscoreBoolean = false
        usedCO2Things.clear()

        val randomYellowOption = pickNextRandomCO2Ting()
        val randomRedOption = pickNextRandomCO2Ting()
        val nextRedOption = pickNextRandomCO2Ting()

        _uiState.value = GameUiState(
            playerID = uiState.value.playerID,
            highscore = _uiState.value.highscore,
            score = 0,
            currentYellowOption = randomYellowOption,
            currentRedOption = randomRedOption,
            nextRedOption = nextRedOption
        )

        preloadImage(uiState.value.currentYellowOption.image)
        preloadImage(uiState.value.currentRedOption.image)
        preloadImage(uiState.value.nextRedOption.image)
    }

    // Tyvstjålet fra ChatGPT
    fun preloadImage(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap = downloadBitmap(url)
            if (bitmap != null) {
                _imageMap.value = _imageMap.value + (url to bitmap)
            }
        }
    }

    // Tyvstjålet fra ChatGPT
    private fun downloadBitmap(imageUrl: String): Bitmap? {
        return try {
            val connection = URL(imageUrl).openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val inputStream = connection.inputStream
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    init {
        viewModelScope.launch {
            CO2Itemrepository.getRandomCO2Items()
            resetGame()
            println(CO2TingListe.size)
        }
    }
}





