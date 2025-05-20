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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.klimaspillet.R
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.zIndex
import com.example.klimaspillet.navigation.Routes

//   ------------------------------------
//   Hovedsageligt ansvarlig: Victor Lotz
//   ------------------------------------

@Composable
fun GameScreen (
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Baggrund
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // Middleground I guess ??? :D   Det er scoren bag content.
        Score()

        // Content
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp)
        ) {
            BackButtonAndTitle(navController)
            CO2Choices()
            RedAndYellowButtons(navController)
        }
    }
}

@Preview
@Composable
fun Preview() {
    CO2Choices()
}


@Composable
fun BackButtonAndTitle(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .padding(10.dp)
    ) {
        // Back button
        Box(
            modifier = Modifier
                .matchParentSize()
        ) {
            IconButton(
                modifier = Modifier
                    .size(50.dp)
                    .align (Alignment.CenterStart),
                onClick = {
                    // Bør Navigation tage sig at navigate()?
                    navController.navigate(Routes.routeHomeScreen)
                }
            ) {
                // For at opnå en drop-shadow effekt af et ikon, indsætter jeg det samme ikon to gange, men gør den første mere blurry.
                Box {
                    Icon(
                        painter = painterResource(id = R.drawable.round_arrow_back_ios_24),
                        contentDescription = null,
                        Modifier
                            .offset(x = (2).dp, y = (1).dp)
                            .blur(3.dp),
                        tint = Color.Gray
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.round_arrow_back_ios_24),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }

        // Title
        Text("Hvad udleder mest CO2?",
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.Center),
            style = TextStyle(
                fontSize = 40.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                textAlign = TextAlign.Center,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.25f), offset = Offset(4f, 4f), blurRadius = 0f
                )
            )
        )
    }
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
    val placement = 480.dp;
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(placement)
            .padding(top = placement-350.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(10f)
                .align (Alignment.Center)
        ) {
            Text("VS",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                textAlign = TextAlign.Center,
                fontSize = 60.sp,
                modifier = Modifier
                    .align (Alignment.Center)
            )
        }
        YellowOption(modifier = Modifier
            .align(Alignment.TopEnd), 7.48f)
        RedOption(modifier = Modifier
            .align(Alignment.BottomStart), 1.2f)
    }
}

@Composable
fun YellowOption(modifier: Modifier, CO2e: Float) {
    Box(
        modifier
            .clip(shape = RoundedCornerShape(100.dp, 0.dp, 0.dp, 100.dp))
            .fillMaxWidth(0.8f)
            .background(Color(0xFFFFCA58))
    ) {
        Image(
            painter = painterResource(id = R.drawable.hakket_oksekoed),
            contentDescription = null,
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
                .border(
                    BorderStroke(8.dp, Color(0xFFFFCA58)),
                    CircleShape
                )
        )
        Text("udleder ${CO2e}kg CO2e",
            fontSize = 12.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
            modifier = Modifier
                .align (Alignment.TopEnd)
                .padding(end = 8.dp, top = 2.dp)
        )
    }
}

@Composable
fun RedOption(modifier: Modifier, CO2e: Float) {
    val CO2e = CO2e
    Box(
        modifier
            .clip(shape = RoundedCornerShape(0.dp, 100.dp, 100.dp, 0.dp))
            .fillMaxWidth(0.8f)
            .background(Color(0xFFFF5858))
    ) {
        Text("udleder ???kg CO2e",
            fontSize = 12.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
            modifier = Modifier
                .align (Alignment.BottomStart)
                .padding(start = 8.dp, bottom = 2.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.kyllingekoed),
            contentDescription = null,
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
                .border(
                    BorderStroke(8.dp, Color(0xFFFF5858)),
                    CircleShape
                )
                .align (Alignment.CenterEnd)
        )
    }
}

@Composable
fun RedAndYellowButtons(navController: NavController) {
    Box(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCA58)),
                modifier = Modifier
                    .fillMaxSize().weight(1f)
                    .padding(end = 10.dp),
                onClick = {
                    navController.navigate(Routes.routeResultsScreen)
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
                    .fillMaxSize().weight(1f)
                    .padding(start = 10.dp),
                onClick = {
                    navController.navigate(Routes.routeResultsScreen)
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
}