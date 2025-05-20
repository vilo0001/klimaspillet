@file:Suppress("SpellCheckingInspection")

package com.example.klimaspillet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klimaspillet.navigation.Navigation
import com.example.klimaspillet.ui.ViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Navigation(viewModel())
        }
    }
}
