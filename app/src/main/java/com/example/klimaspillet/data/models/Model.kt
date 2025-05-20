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

