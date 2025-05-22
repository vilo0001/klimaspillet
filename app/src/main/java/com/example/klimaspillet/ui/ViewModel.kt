@file:Suppress("SpellCheckingInspection")

package com.example.klimaspillet.ui

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.klimaspillet.data.models.CO2TingListe
import com.example.klimaspillet.data.models.CO2Ting
import com.example.klimaspillet.data.repository.UserInfo
import com.example.klimaspillet.navigation.Routes
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//   ------------------------------------
//   Hovedsageligt ansvarlig: Victor Lotz
//   ------------------------------------

data class GameUiState(
    val playerID: String = "",
    val highscore: Int = 0,
    val score: Int = 0,
    val currentYellowOption: CO2Ting = CO2TingListe[0],
    val currentRedOption: CO2Ting = CO2TingListe[1],
    var crownMoverValue:Int = 0
)

class ViewModel : ViewModel() {
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


    fun crownMoverFunction(currentScore: Int) {
            uiState.value.crownMoverValue = when {
                currentScore in 0..4 -> 0
                currentScore in 5..9 -> 1
                currentScore in 10..100 -> 2
                else -> uiState.value.crownMoverValue // Fallback to existing value
            }
    }

    var numberCrownMover =  uiState.value.crownMoverValue;

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
        if(uiState.value.currentRedOption.CO2e > uiState.value.currentYellowOption.CO2e) {
            nextQuestion()
        } else {
            endGame(navController)
        }
    }

    fun chooseYellowOption(navController: NavController) {
        if(uiState.value.currentRedOption.CO2e < uiState.value.currentYellowOption.CO2e) {
            nextQuestion()
        } else {
            endGame(navController)
        }
    }

    fun endGame(navController: NavController) {
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

        _uiState.value = GameUiState(
            playerID = uiState.value.playerID,
            highscore = newHighscore,
            score = newScore,
            currentYellowOption = uiState.value.currentRedOption,
            currentRedOption = pickRandomThingAndShuffle()
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
        resetGame()
    }
}





