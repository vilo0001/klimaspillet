package com.example.klimaspillet.data.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klimaspillet.data.models.Class
import com.example.klimaspillet.data.models.Student
import com.example.klimaspillet.viewmodels.Viewmodel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
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
