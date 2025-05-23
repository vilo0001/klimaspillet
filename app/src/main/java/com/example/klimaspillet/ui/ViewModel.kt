@file:Suppress("SpellCheckingInspection")

package com.example.klimaspillet.ui

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
import com.example.klimaspillet.data.repository.StudentRepository

//   ------------------------------------
//   Hovedsageligt ansvarlig: Victor Lotz
//   ------------------------------------

class ViewModel : ViewModel() {
    val CO2Itemrepository = CO2ItemsRepository()
    val studentRepository = StudentRepository()

    fun addStudent(name: String, classCode: String, emoji: String) {
        viewModelScope.launch {
            if (!studentRepository.getClassByClassCode(classCode).isEmpty()) {
                studentRepository.newStudent(name, classCode, emoji)
            } else {
                println("Klassekode eksisterer ikke")
            }
        }
    }

    // Game UI state
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // Liste af allerede brugte CO2-ting i spillet.
    private var usedCO2Things: MutableSet<CO2Ting> = mutableSetOf()

    // gameEnded boolean så man kan fortsætte fra spil (f.eks. efter at havde gået tilbage til home screen)
    private var gameEnded = false;
    // Altid ny highscore, hvis highscore er 0 (eller er ny bruger og derfor har 0).
    // Ellers newHighscore default false.
    var newHighscoreBoolean = uiState.value.highscore != 0
    var finalScore = 0;

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

    //AndreasRG:
    // Henter talet fra crownMoverFunction
    val numberCrownMover: Int
        get() = uiState.value.crownMoverValue

    private fun pickRandomThingAndShuffle(): CO2Ting {
        // Vælg en ny tilfældig CO2 ting indtil der findes én som ikke er brugt tidligere.
        val randomCO2Ting = CO2TingListe.random()
        if (usedCO2Things.contains(randomCO2Ting)) {
            return pickRandomThingAndShuffle()
        } else {
            usedCO2Things.add(randomCO2Ting)
            return randomCO2Ting
        }
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
        finalScore = uiState.value.score;
        gameEnded = true;
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
            nextRedOption = pickRandomThingAndShuffle()
        )
    }

    fun resetGame() {
        gameEnded = false
        newHighscoreBoolean = false
        usedCO2Things.clear()

        val randomYellowOption = pickRandomThingAndShuffle()
        val randomRedOption = pickRandomThingAndShuffle()

        _uiState.value = GameUiState(
            playerID = uiState.value.playerID,
            highscore = _uiState.value.highscore,
            score = 0,
            currentYellowOption = randomYellowOption,
            currentRedOption = randomRedOption)
    }

    // Hvis spillet er sluttet tidligere; reset. Ellers fortsæt.
    fun startGame(navController: NavController) {
        if(gameEnded) {
            resetGame()
            gameEnded = !gameEnded
        }
        navController.navigate(Routes.routeGameScreen)
    }

    init {
        viewModelScope.launch {
            CO2Itemrepository.getRandomCO2Items()
            resetGame()
        }
    }
}





