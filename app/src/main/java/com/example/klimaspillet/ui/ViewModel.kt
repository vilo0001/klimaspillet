@file:Suppress("SpellCheckingInspection")

package com.example.klimaspillet.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.klimaspillet.data.models.CO2TingListe
import com.example.klimaspillet.data.models.CO2Ting

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

    // Set of CO2 things used in the game
    private var usedCO2Things: MutableSet<CO2Ting> = mutableSetOf()

    private var yellowOption: CO2Ting = uiState.value.currentYellowOption
    private var redOption: CO2Ting = uiState.value.currentRedOption
    private var score: Int = uiState.value.score

    private fun pickRandomThingAndShuffle(): CO2Ting {
        // Continue picking up a new random thing until you get one that hasn't been used before
        yellowOption = CO2TingListe.random()
        if (usedCO2Things.contains(yellowOption)) {
            return pickRandomThingAndShuffle()
        } else {
            usedCO2Things.add(yellowOption)
            return yellowOption
        }
    }

    fun nextQuestion() {
        _uiState.value = GameUiState(
            score++,
            currentYellowOption = pickRandomThingAndShuffle(),
            currentRedOption = pickRandomThingAndShuffle()
        )
    }

    fun endGame() {

    }

    fun resetGame() {
        usedCO2Things.clear()
        //_uiState.value = GameUiState(currentYellowOption = pickRandomThingAndShuffle())
    }

    init {
        resetGame()
    }
}