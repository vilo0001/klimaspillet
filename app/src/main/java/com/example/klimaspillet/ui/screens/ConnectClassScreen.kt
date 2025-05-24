@file:Suppress("SpellCheckingInspection")

package com.example.klimaspillet.ui.screens

import android.util.Log
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.klimaspillet.R
import com.example.klimaspillet.data.repository.MyClassManager
import com.example.klimaspillet.navigation.Routes
import com.example.klimaspillet.ui.ViewModel

// MAGNUS GIEMSA
@Composable
fun ConnectClassScreen(
    viewModel: ViewModel = viewModel(),
    navController: NavController
) {
    val gameUIState by viewModel.uiState.collectAsState()
    var showEmojiPicker by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var classCode by remember { mutableStateOf("") }
    var selectedEmoji by remember { mutableIntStateOf(R.drawable.emoji1) }
    val manager = remember { MyClassManager() }
    val classCodes = remember { mutableStateOf<List<String>>(emptyList()) }
    val isValidCode = classCodes.value.contains(classCode)
    LaunchedEffect(Unit) {
        manager.loadClassCodes()
        classCodes.value = manager.classCodes
        Log.d("Magnus", "Klassekoder: ${classCodes.value}")
    }
    BackButton(navController)
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Title()
            Spacer(modifier = Modifier.height(32.dp))
            // Navn og Klassekode felter
            ClassInputFields(
                name = name,
                onNameChange = { name = it },
                classCode = classCode,
                onClassCodeChange = { classCode = it },
                isValidCode = isValidCode
            )
            Spacer(modifier = Modifier.height(32.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                EmojiButton(
                    emojiId = selectedEmoji,
                    onClick = { showEmojiPicker = true }
                )
                Spacer(modifier = Modifier.width(16.dp))
                EmojiInfo()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            OkButton(navController = navController, enabled = isValidCode)
        }

        if (showEmojiPicker) {
            Dialog(onDismissRequest = { showEmojiPicker = false }) {
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

@Composable
fun Title () {
    Text(
        text = "Tilslut klasse",
        fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
        fontSize = 48.sp,
        color = Color.White,
        modifier = Modifier.shadow(40.dp, RoundedCornerShape(40.dp))
    )
}


// Magnus Giemsa
// Emoji knap, med emojiId som er den emoji man har valgt.
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

@Composable
fun EmojiInfo() {
    var showDialogEmoji by remember { mutableStateOf(false) }
    Icon(
        painter = painterResource(id = R.drawable.textinfo),
        contentDescription = "InfoEmoji",
        modifier = Modifier
            .size(24.dp)
            .clickable { showDialogEmoji = true },
        tint = Color.Black
    )

    if (showDialogEmoji) {
        AlertDialog(
            onDismissRequest = { showDialogEmoji = false },
            text = {
                Text("Vælg din avatar",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                        color = Color(0, 0, 0, 255)
                    ))
            },
            confirmButton = {}
        )
    }
}

// Tekst knapper (Navn og klassekode)
//Magnus Giemsa
@Composable
fun ClassInputFields(
    name: String,
    onNameChange: (String) -> Unit,
    classCode: String,
    onClassCodeChange: (String) -> Unit,
    isValidCode: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Navn
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Navn", fontFamily = FontFamily(Font(R.font.bagel_fat_one))) },
                textStyle = LocalTextStyle.current.copy(
                    fontFamily = FontFamily(Font(R.font.bagel_fat_one))
                ),
                singleLine = true,
                shape = RoundedCornerShape(40.dp),
                modifier = Modifier
                    .width(250.dp)
                    .shadow(30.dp, RoundedCornerShape(40.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        // Klassekode
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = classCode,
                onValueChange = onClassCodeChange,
                label = {
                    Text("Klassekode", fontFamily = FontFamily(Font(R.font.bagel_fat_one)))
                },
                textStyle = LocalTextStyle.current.copy(
                    fontFamily = FontFamily(Font(R.font.bagel_fat_one))
                ),
                singleLine = true,
                shape = RoundedCornerShape(40.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = if (isValidCode) Color(0xFF62F88C) else Color(253, 113, 113, 255),
                    unfocusedContainerColor = if (isValidCode) Color(0xFF62F88C) else Color.White
                ),
                modifier = Modifier
                    .width(250.dp)
                    .shadow(30.dp, RoundedCornerShape(40.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

// Magnus Giemsa
@Composable
fun OkButton(navController: NavController, enabled: Boolean) {
    Button(
        onClick = {
            navController.navigate(Routes.routeHomeScreen)
        },
        enabled = enabled,
        modifier = Modifier
            .size(120.dp)
            .shadow(8.dp, shape = RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6EFF64),
            disabledContainerColor = Color(0xFFBDBDBD),
            disabledContentColor = Color.White
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = "OK",
            fontSize = 56.sp,
            fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
            modifier = Modifier.shadow(20.dp, RoundedCornerShape(40.dp))
        )
    }
}

@Composable
fun BackButton(navController: NavController) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                navController.navigate(Routes.routeHomeScreen)
            }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.round_arrow_back_ios_24),
            contentDescription = null,
            modifier = Modifier
                .offset(x = 2.dp, y = 1.dp)
                .blur(3.dp),
            tint = Color.Gray
        )
        Icon(
            painter = painterResource(id = R.drawable.round_arrow_back_ios_24),
            contentDescription = "Back",
            tint = Color.White
        )
    }
}