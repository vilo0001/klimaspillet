package com.example.klimaspillet.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.klimaspillet.R

//   ------------------------------------
//   Hovedsageligt ansvarlig: Victor Lotz
//   ------------------------------------

@Composable
fun GameScreen (navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Baggrund
        Image(
            painter = painterResource(id = R.drawable.background), // Replace with your image resource
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // Middleground I guess ??? :D   Det er scoren bag content og back button.
        Score()
        Box() {
            Button(onClick = {

            }) {
                Text("<")
            }
        }

        // Content
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .padding(bottom = 50.dp)
        ) {
            Title()
            CO2Choices()
            RedAndYellowButtons()
        }
    }
}

@Composable
fun Title() {
    Text("Hvad udleder mest CO2?",
        modifier = Modifier
            .fillMaxWidth(0.8f),
        fontSize = 40.sp,
        color = Color.White,
        fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
        textAlign = TextAlign.Center)
}

@Composable
fun Score() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text("0",
            fontSize = 256.sp,
            color = Color.Black.copy(alpha = 0.25f),
            fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 350.dp))
    }
}

@Composable
fun CO2Choices() {
    Box() {
        Column {
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

    }
}

@Composable
fun RedAndYellowButtons() {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCA58)),
            modifier = Modifier
                .fillMaxSize().weight(1f),
            onClick = {

            }
        ) {
            Row() {
                Text("IKON")
                Text(
                    "Gul",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.bagel_fat_one))
                )
            }
        }

        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5858)),
            modifier = Modifier
                .fillMaxSize().weight(1f),
            onClick = {

            }
        ) {
            Row() {
                Text("IKON")
                Text(
                    "Rød",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.bagel_fat_one))
                )
            }
        }
    }
}