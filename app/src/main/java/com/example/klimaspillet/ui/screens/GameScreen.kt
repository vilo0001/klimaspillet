@file:Suppress("SpellCheckingInspection")

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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.IconButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.klimaspillet.data.models.CO2Ting
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.time.temporal.TemporalQueries.offset
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.klimaspillet.R
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.klimaspillet.navigation.Routes
import com.example.klimaspillet.ui.ViewModel
import java.time.format.TextStyle
import kotlin.Function as Function

//   ------------------------------------
//   Hovedsageligt ansvarlig: Victor Lotz
//   ------------------------------------

@Composable
fun GameScreen (
    viewModel: ViewModel,
    navController: NavController
) {
    val gameUIState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Middleground I guess ??? :D   Det er scoren bag content.
        LaunchedEffect(gameUIState.score) {
            viewModel.crownMoverFunction(gameUIState.score)
        }
        Score(currentScore = gameUIState.score, viewModel.newHighscoreBoolean, viewModel.numberCrownMover)


        // Content
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp)
        ) {
            BackButtonAndTitle(navController)
            CO2Choices(yellowOption = gameUIState.currentYellowOption, redOption = gameUIState.currentRedOption)
            RedAndYellowButtons(navController, viewModel)
        }
    }
}

@Preview
@Composable
fun Preview() {
    //CO2Choices()
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
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 40.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                textAlign = TextAlign.Center,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.25f),
                    offset = Offset(4f, 4f),
                    blurRadius = 0f
                )
            )
        )
    }
}

@Composable
fun Score(currentScore: Int, newHighscore: Boolean, crownMover: Int) {
    val crownMoverState = remember { mutableStateOf(crownMover) }

    if (newHighscore) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Text(
                currentScore.toString(),
                fontSize = 256.sp,
                color = Color(0xFFFF9000).copy(alpha = 0.50f),
                fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 350.dp)
            )
        }
        Box {
            when (crownMoverState.value) {
                0 -> Image(
                    painter = painterResource(id = R.drawable.highscorecrown2),
                    modifier = Modifier.fillMaxWidth()
                        .height(31.dp)
                        .width(25.dp)
                        .rotate(34F)
                        .offset(x = 145.dp, y = 125.dp),
                    contentDescription = null,
                )
                1 -> Image(
                    painter = painterResource(id = R.drawable.highscorecrown2),
                    modifier = Modifier.fillMaxWidth()
                        .height(31.dp)
                        .width(25.dp)
                        .rotate(34F)
                        .offset(x = 180.dp, y = 125.dp),
                    contentDescription = null,
                )
                2 -> Image(
                    painter = painterResource(id = R.drawable.highscorecrown2),
                    modifier = Modifier.fillMaxWidth()
                        .height(31.dp)
                        .width(25.dp)
                        .rotate(34F)
                        .offset(x = 80.dp, y = 15.dp),
                    contentDescription = null,
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                currentScore.toString(),
                fontSize = 256.sp,
                color = Color.Black.copy(alpha = 0.25f),
                fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 350.dp)
            )
        }
    }
}


@Composable
fun CO2Choices(yellowOption: CO2Ting, redOption: CO2Ting) {
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
            .align(Alignment.TopEnd), yellowOption)
        RedOption(modifier = Modifier
            .align(Alignment.BottomStart), redOption)
    }
}

@Composable
fun YellowOption(modifier: Modifier, yellowOption: CO2Ting) {
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
        Text("udleder ${yellowOption.CO2e}kg CO2e",
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
fun RedOption(modifier: Modifier, redOption: CO2Ting) {
    Box(
        modifier
            .clip(shape = RoundedCornerShape(0.dp, 100.dp, 100.dp, 0.dp))
            .fillMaxWidth(0.8f)
            .background(Color(0xFFFF5858))
    ) {
        Text("udleder ${redOption.CO2e}kg CO2e",
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
fun RedAndYellowButtons(navController: NavController, viewModel: ViewModel) {
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
            // Gul knap
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCA58)),
                modifier = Modifier
                    .fillMaxSize().weight(1f)
                    .padding(end = 10.dp),
                onClick = {
                    viewModel.chooseYellowOption(navController)
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
            // Rød knap
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5858)),
                modifier = Modifier
                    .fillMaxSize().weight(1f)
                    .padding(start = 10.dp),
                onClick = {
                    viewModel.chooseRedOption(navController)
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
