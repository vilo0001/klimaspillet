package com.example.klimaspillet.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
import com.example.klimaspillet.R



@Composable
fun ConnectClassScreen(navController: NavController) {
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
            Title()

            Spacer(modifier = Modifier.height(32.dp))

            ClassInputFields()

            Spacer(modifier = Modifier.height(32.dp))

            EmojiButton()
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            OkButton()
        }
    }
}

@Composable
fun Background () {
    Image(
        painter = painterResource(id = R.drawable.background),
        contentDescription = "background",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}


@Composable
fun Title() {
    Text(
        text = "Tilslut klasse",
        fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
        fontSize = 48.sp,
        color = Color.White,
        modifier = Modifier.shadow(40.dp, RoundedCornerShape(40.dp))
    )
}

// Tekst knapper (Navn og klassekode)
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


@Composable
fun EmojiButton() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .shadow(8.dp, shape = RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.emoji),
            contentDescription = "Emoji",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(60.dp)
        )
    }
}

@Composable
fun OkButton() {
    Button(
        onClick = {  },
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