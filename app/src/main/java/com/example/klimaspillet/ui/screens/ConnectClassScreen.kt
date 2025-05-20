package com.example.klimaspillet.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.klimaspillet.navigation.Routes
import com.example.klimaspillet.R


// MAGNUS GIEMSA
@Composable
fun ConnectClassScreen(navController: NavController) {
    var showEmojiPicker by remember { mutableStateOf(false) }
    var selectedEmoji by remember { mutableIntStateOf(R.drawable.emoji1) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Background()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Tilslut klasse",
                fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                fontSize = 48.sp,
                color = Color.White,
                modifier = Modifier.shadow(40.dp, RoundedCornerShape(40.dp))
            )

            Spacer(modifier = Modifier.height(32.dp))

            ClassInputFields()

            Spacer(modifier = Modifier.height(32.dp))


            EmojiButton(
                emojiId = selectedEmoji,
                onClick = { showEmojiPicker = true }
            )
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            OkButton(navController = navController)
        }

        // Dialog for Emoji Picker
        if (showEmojiPicker) {
            Dialog(onDismissRequest = {showEmojiPicker = false}) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(Color.White, shape = RoundedCornerShape(20.dp))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val emojiOptions = listOf(
                        R.drawable.emoji1, R.drawable.emoji2,
                        R.drawable.emoji3, R.drawable.emoji4,
                        R.drawable.emoji5, R.drawable.emoji6,
                        R.drawable.emoji7, R.drawable.emoji8
                    )

                    emojiOptions.chunked(4).forEach { row ->
                        Row {
                            row.forEach { emojiId ->
                                IconButton(onClick = {
                                    selectedEmoji = emojiId
                                    showEmojiPicker = false
                                }) {
                                    Image(
                                        painter = painterResource(id = emojiId),
                                        contentDescription = null,
                                        modifier = Modifier.size(40.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

// Magnus Giemsa
@Composable
fun EmojiButton(emojiId: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .shadow(8.dp, shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = emojiId),
            contentDescription = "Emoji",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(60.dp)
        )
    }
}




// Magnus Giemsa
@Composable
fun Background () {
    Image(
        painter = painterResource(id = R.drawable.background),
        contentDescription = "background",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

// Tekst knapper (Navn og klassekode)
//Magnus Giemsa
@Composable
fun ClassInputFields() {
    val name = remember { mutableStateOf("") }
    val classCode = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Navn", fontFamily = FontFamily(Font(R.font.bagel_fat_one)) ) },
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily(Font(R.font.bagel_fat_one))
            ),
            singleLine = true,
            shape = RoundedCornerShape(40.dp),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(30.dp, RoundedCornerShape(40.dp)),
        )

        TextField(
            value = classCode.value,
            onValueChange = { classCode.value = it },
            label = { Text("Klassekode", fontFamily = FontFamily(Font(R.font.bagel_fat_one))) },
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily(Font(R.font.bagel_fat_one))
            ),
            singleLine = true,
            shape = RoundedCornerShape(40.dp),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(30.dp, RoundedCornerShape(40.dp))
        )
    }
}






// Magnus Giemsa
@Composable
fun OkButton(navController: NavController) {
    Button(
        onClick = {
            // Victor Lotz
            navController.navigate(Routes.routeHomeScreen)
        },
        modifier = Modifier
            .size(100.dp)
            .shadow(8.dp, shape = RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6EFF64)
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = "OK",
            fontSize = 56.sp,
            fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
            color = Color.White,
            modifier = Modifier.shadow(20.dp, RoundedCornerShape(40.dp))
        )
    }
}

