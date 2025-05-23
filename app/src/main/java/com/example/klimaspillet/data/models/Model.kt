@file:Suppress("SpellCheckingInspection")

package com.example.klimaspillet.data.models

import com.google.firebase.firestore.DocumentId

//Andreas B
data class Student(
    val name: String = "",
    val classCode: String = "",
    val emoji: String = "",
    val highscore: Int = 0,
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
    val name: String = "",
    val CO2: Double = 0.0,
    @DocumentId var id: String? = null
) {
    override fun toString(): String {
        return "CO2Ting(id=$id, name=$name, CO2e=$CO2)"
    }
}

data class GameUiState(
    val playerID: String = "",
    val highscore: Int = 0,
    val score: Int = 0,
    val currentYellowOption: CO2Ting = CO2Ting("", 0.0),
    val currentRedOption: CO2Ting = CO2Ting("", 0.0),
    var crownMoverValue:Int = 0,
    ////////////////////////
    var thirdPlaceName:String = "",
    var secondPlaceName:String = "",
    var firstPlaceName:String = "",
    var scoreThirdPlace:Int = 0,
    var scoreSecondPlace:Int = 0,
    var scoreFirstPlace:Int = 0,
    val yourClassCode:String = "",
    var hasClass:Boolean? = null,
)

//hasClass -> if(classCode)
//order by score, top 3 get()

