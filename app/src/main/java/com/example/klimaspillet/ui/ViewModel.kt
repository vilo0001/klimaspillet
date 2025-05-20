package com.example.klimaspillet.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GameUiState(
    val score: Int = 0,
    val currentYellowOption: String = ""
)

class ViewModel : ViewModel() {
    // Game UI state
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private lateinit var currentYellowOption: String
    private lateinit var currentRedOption: String

    private val CO2Things = listOf<String>("Oksekød", "Kyllingekød", "Flytur til Paris");

    // Set of CO2 things used in the game
    private var usedCO2Things: MutableSet<String> = mutableSetOf()

    private fun pickRandomThingAndShuffle(): String {
        // Continue picking up a new random thing until you get one that hasn't been used before
        currentYellowOption = CO2Things.random()
        if (usedCO2Things.contains(currentYellowOption)) {
            return pickRandomThingAndShuffle()
        } else {
            usedCO2Things.add(currentYellowOption)
            return currentYellowOption
        }
    }

    fun resetGame() {
        usedCO2Things.clear()
        _uiState.value = GameUiState(currentYellowOption = pickRandomThingAndShuffle())
    }

    init {
        resetGame()
    }
}