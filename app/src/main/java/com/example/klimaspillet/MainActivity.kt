@file:Suppress("SpellCheckingInspection")

package com.example.klimaspillet

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klimaspillet.navigation.Navigation


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            setContent {
                //AndreasRG:
                Box(modifier = Modifier.fillMaxSize().clipToBounds().height(2400.dp)) { // Clips overflowing parts
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

