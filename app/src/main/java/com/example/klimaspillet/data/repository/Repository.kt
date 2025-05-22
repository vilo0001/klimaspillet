package com.example.klimaspillet.data.repository

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klimaspillet.data.models.Class
import com.example.klimaspillet.data.models.Student
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.math.log

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



    suspend fun getAllClassCodes(): List<String> {
        val snapshot = db.collection("Classes")
            .get()
            .await()

        val codes = snapshot.documents.mapNotNull { doc ->
            doc.getString("classCode")
        }
        println(codes)
        Log.d("ClassCodes", "Fetched codes: $codes")
        return codes
    }

}


object ClassCodeStore {
    private val db = FirebaseFirestore.getInstance()
    var classCodes: List<String> = emptyList()
        private set

    suspend fun loadClassCodes() {
        val snapshot = db.collection("Classes").get().await()
        classCodes = snapshot.documents.mapNotNull { it.getString("classCode") }
    }
}

//Andreas B
class TestViewmodel : ViewModel() {
    private val repository = StudentRepository()

    fun addStudent(name: String, classCode: String, emoji: String) {
        viewModelScope.launch {
            if (!repository.getClassByClassCode(classCode).isEmpty()) {
                repository.newStudent(name, classCode, emoji)
            } else {
                println("Klassekode eksisterer ikke")
            }
        }
    }
}