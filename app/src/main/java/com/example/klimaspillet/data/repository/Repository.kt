package com.example.klimaspillet.data.repository

import android.util.Log
import com.example.klimaspillet.data.models.CO2Ting
import com.example.klimaspillet.data.models.Class
import com.example.klimaspillet.data.models.Student
import com.example.klimaspillet.ui.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

//Andreas B
class StudentRepository() {
    val db = Firebase.firestore

    suspend fun newStudent(name: String, classCode: String, emoji: String, highscore: Int = 0): String {
        val student = Student(name, classCode, emoji, highscore);
        var studentID = "";
        // Add a new document with a generated ID
        db.collection("Students")
            .add(student)
            .addOnSuccessListener { documentReference ->
                studentID = documentReference.id
            }
            .await()
        // Victor Lotz
        return studentID
    }

    //Andreas B
    suspend fun getClassByClassCode(classCode: String):List<Class> {
        val getCode = db.collection("Classes")
            .whereEqualTo("classCode", classCode)
            .get()
            .await()
            .toObjects(Class::class.java)
        return getCode
    }

    //AndreasRG:
    suspend fun getClassNameByClassCode(classCode: String): String? {
        val getCode = db.collection("Classes")
            .whereEqualTo("classCode", classCode)
            .get()
            .await()
            .toObjects(Class::class.java)

        return getCode.firstOrNull()?.name
    }

    //AndreasRG:
    suspend fun getStudentsFromClass(classCode: String):List<Student> {
        val getStudents = db.collection("Students")
            .whereEqualTo("classCode", classCode)
            .get()
            .await()
            .toObjects(Student::class.java)
        return getStudents
    }

    // Victor Lotz
    fun updateHighscore(studentID: String, highscore: Int) {
        db.collection("Students").document(studentID)
            .update("highscore", highscore)
    }

    // Magnus Giemsa
    suspend fun getAllClassCodes(): List<String> {
        val classesSnapshot = db.collection("Classes")
            .get()
            .await()

        return classesSnapshot.documents.mapNotNull { it.getString("classCode") }
    }
}

// Magnus Giemsa
class MyClassManager {
    private val repository = StudentRepository()
    var classCodes: MutableList<String> = mutableListOf()

    suspend fun loadClassCodes() {
        classCodes = repository.getAllClassCodes().toMutableList()
        Log.d("Magnus", "Hentet koder fra Repository: $classCodes")
    }
}

class CO2ItemsRepository {
    val db = Firebase.firestore

    suspend fun getRandomCO2Items():List<CO2Ting> {
        val co2Items = db.collection("CO2_Items")
            .get()
            .await()
            .toObjects(CO2Ting::class.java)
        CO2TingListe.addAll(co2Items)
        CO2TingListe.shuffle();
        return co2Items
    }
}

val CO2TingListe = mutableListOf<CO2Ting>()