package com.example.klimaspillet.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.Rgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.klimaspillet.R

@Composable
fun GameScreen (navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.background), // Replace with your image resource
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // Foreground Content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            TitleAndBackButton()
            Score()
            CO2Choices()
            RedAndYellowButtons()
        }
    }
}

@Composable
fun TitleAndBackButton() {
    Row{
        Button(onClick = {

        }) { Text("<") }
        Text("Hvad udleder mest CO2?",
            fontSize = 40.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.bagel_fat_one)))
    }
}

@Composable
fun Score() {
    Text("0",
        fontSize = 40.sp,
        color = Color.White,
        fontFamily = FontFamily(Font(R.font.bagel_fat_one)))
}

@Composable
fun CO2Choices() {
    Row {
        Image(
            painter = painterResource(id = R.drawable.hakket_oksekoed), // Replace with your image resource
            contentDescription = null,
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)
                .border(
                    BorderStroke(5.dp, Color(0xFFFFCA58)),
                    CircleShape
                )
        )
        Text("udleder 7,48kg CO2e",
            fontSize = 14.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.bagel_fat_one)))
    }

    Text("VS",
        fontSize = 40.sp,
        color = Color.White,
        fontFamily = FontFamily(Font(R.font.bagel_fat_one)))

    Row {
        Text("udleder ???kg CO2e",
            fontSize = 14.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.bagel_fat_one)))
        Image(
            painter = painterResource(id = R.drawable.kyllingekoed), // Replace with your image resource
            contentDescription = null,
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)
                .border(
                    BorderStroke(5.dp, Color(0xFFFF5858)),
                    CircleShape
                )
        )
    }
}

@Composable
fun RedAndYellowButtons() {
    Row {
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCA58)),
            onClick = {

            }
        ) { Text("Gul",
            fontSize = 24.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.bagel_fat_one))) }

        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5858)),
            onClick = {

            }
        ) { Text("Rød",
            fontSize = 24.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.bagel_fat_one))) }
    }
}