package com.example.klimaspillet.data.repository

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "game_prefs")

class UserInfo(private val context: Context) {
    private val dataStore = context.dataStore

    // Keys
    private object PreferencesKeys {
        val HIGHSCORE = intPreferencesKey("high_score")
        val PLAYER_ID = stringPreferencesKey("player_id")
    }

    // Save high score
    suspend fun saveHighScore(score: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.HIGHSCORE] = score
        }
    }

    // Get high score (Flow)
    val highScore: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.HIGHSCORE] ?: 0
        }

    // Save player ID
    suspend fun savePlayerId(id: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.PLAYER_ID] = id
        }
    }

    // Get player ID (Flow)
    val playerId: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.PLAYER_ID] ?: ""
        }
}