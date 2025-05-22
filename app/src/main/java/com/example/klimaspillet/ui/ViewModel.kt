@file:Suppress("SpellCheckingInspection")

package com.example.klimaspillet.ui

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.klimaspillet.data.models.CO2TingListe
import com.example.klimaspillet.data.models.CO2Ting
import com.example.klimaspillet.navigation.Routes
import kotlin.random.Random

//   ------------------------------------
//   Hovedsageligt ansvarlig: Victor Lotz
//   ------------------------------------

data class GameUiState(
    val score: Int = 0,
    val currentYellowOption: CO2Ting = CO2TingListe[0],
    val currentRedOption: CO2Ting = CO2TingListe[1],
)

class ViewModel : ViewModel() {
    // Game UI state
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // Liste af allerede brugte CO2-ting i spillet.
    private var usedCO2Things: MutableSet<CO2Ting> = mutableSetOf()

    private var currentScore = uiState.value.score;

    private fun pickRandomThingAndShuffle(): CO2Ting {
        // Vælg en ny tilfældig CO2 ting indtil der findes én som ikke er brugt tidligere.
        val randomCO2Ting = CO2TingListe.random()
        println("pickRandomThingAndShuffle() called.")
        if (usedCO2Things.contains(randomCO2Ting)) {
            return pickRandomThingAndShuffle()
        } else {
            usedCO2Things.add(randomCO2Ting)
            println("Random: $randomCO2Ting")
            return randomCO2Ting
        }
    }

    // Man kunne også lave "chooseOption(color: String)", men det ved jeg ikke om bliver forvirrende?
    fun chooseRedOption() {
        if(uiState.value.currentRedOption.CO2e > uiState.value.currentYellowOption.CO2e) {
            nextQuestion()
        } else {
            endGame()
        }
    }

    fun chooseYellowOption() {
        if(uiState.value.currentRedOption.CO2e < uiState.value.currentYellowOption.CO2e) {
            nextQuestion()
        } else {
            endGame()
        }
    }

    fun nextQuestion() {
        _uiState.value = GameUiState(
            score = uiState.value.score+1,
            currentYellowOption = uiState.value.currentRedOption,
            currentRedOption = pickRandomThingAndShuffle()
        )
    }

    fun endGame() {
        // Navigate til resultsScreen
        println("End game")
    }

    fun resetGame() {
        val randomYellowOption = pickRandomThingAndShuffle()
        val randomRedOption = pickRandomThingAndShuffle()

        usedCO2Things.clear()
        _uiState.value = GameUiState(score = 0, currentYellowOption = randomYellowOption, currentRedOption = randomRedOption)
    }

    init {
        resetGame()
    }
}





