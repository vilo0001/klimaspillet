@file:Suppress("SpellCheckingInspection")

package com.example.klimaspillet.data.models

import com.google.firebase.firestore.DocumentId

//Andreas B
data class Student(
    val name: String = "",
    val classCode: String = "",
    val emoji: String = "",
    val highScore: Int = 0,
    @DocumentId var documentId: String? = null
)

//Andreas B og Victor
data class Class (
    val classCode: String = "",
    val name: String = "",
    val school: String = "",
    @DocumentId var documentId: String? = null
)

class CO2Ting(
    val id: Int,
    val name: String,
    val CO2e: Float
) {
    override fun toString(): String {
        return "CO2Ting(id=$id, name=$name, CO2e=$CO2e)"
    }
}

val CO2TingListe = mutableListOf<CO2Ting>(
    CO2Ting(0, "100g oksekød", 7.48f),
    CO2Ting(1, "100g kyllingekød", 1.2f),
    CO2Ting(2, "iPhone 16 Plus", 60f),
    CO2Ting(3, "Fly til Paris", 267f),
    CO2Ting(4, "Gucci Kashmir", 30f),
    CO2Ting(5, "10 timer TikTok", 1.7f),
    CO2Ting(6, "Vaske tøj", 1f),
    CO2Ting(7, "10km i bil", 2f),
    CO2Ting(8, "8 timer gaming", 1.4f),
    CO2Ting(1, "100g kyllingekød", 1.8f),
    CO2Ting(2, "iPhone 16 Plus", 65f),
    CO2Ting(3, "Fly til Paris", 269f),
    CO2Ting(4, "Gucci Kashmir", 32f),
    CO2Ting(5, "10 timer TikTok", 1.9f),
    CO2Ting(6, "Vaske tøj", 1.1f),
    CO2Ting(7, "10km i bil", 2.1f),
    CO2Ting(8, "8 timer gaming", 1.3f),
)