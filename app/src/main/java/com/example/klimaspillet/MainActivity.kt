@file:Suppress("SpellCheckingInspection")

package com.example.klimaspillet

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klimaspillet.ui.ViewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import com.example.klimaspillet.navigation.Navigation
import com.example.klimaspillet.ui.screens.ConnectClassScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.klimaspillet.navigation.Navigation
import com.example.klimaspillet.ui.screens.ConnectClassScreen
import com.example.klimaspillet.ui.theme.KlimaspilletTheme
import androidx.core.net.toUri



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            setContent {
                //AndreasRG:
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(2400.dp),
                ) {
                    AndroidView(
                        factory = { context ->
                            VideoView(context).apply {
                                val videoUri =
                                    Uri.parse("android.resource://" + context.packageName + "/" + R.raw.baggrundanimation)
                                setVideoURI(videoUri)
                                setOnPreparedListener {
                                    it.isLooping = true
                                    start()
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                            .height(2400.dp)
                    )
                }
                Navigation(viewModel())
            }
        }
    }
}

