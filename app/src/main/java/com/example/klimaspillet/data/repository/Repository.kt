package com.example.klimaspillet.data.repository

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klimaspillet.data.models.CO2Ting
import com.example.klimaspillet.data.models.Class
import com.example.klimaspillet.data.models.Student
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

//Andreas B
class StudentRepository {
    val db = Firebase.firestore

    suspend fun newStudent(name: String, classCode: String, emoji: String) {
        val student = Student(name, classCode, emoji, 0);
        // Add a new document with a generated ID
        db.collection("Students")
            .add(student)
            .await()
    }
    
    //Andreas B
    suspend fun getClassByClassCode(classCode: String):List<Class> {
        val getCode = db.collection("Classes")
            .whereEqualTo("classCode", classCode)
            .get()
            .await()
            .toObjects(Class::class.java)
        println(getCode)
        return getCode
    }
}

class CO2ItemsRepository {
    val db = Firebase.firestore

    suspend fun getRandomCO2Items():List<CO2Ting> {
        val co2Items = db.collection("CO2_Items")
            .limit(100)
            .get()
            .await()
            .toObjects(CO2Ting::class.java)
        println(co2Items)
        CO2TingListe.addAll(co2Items)
        return co2Items
    }
}

val CO2TingListe = mutableListOf<CO2Ting>()