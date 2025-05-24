@file:Suppress("SpellCheckingInspection")

package com.example.klimaspillet.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.zIndex
import com.example.klimaspillet.data.models.CO2Ting
import com.example.klimaspillet.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import coil.compose.rememberAsyncImagePainter
import com.example.klimaspillet.navigation.Routes
import com.example.klimaspillet.ui.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import androidx.core.graphics.drawable.toDrawable

//   ------------------------------------
//   Hovedsageligt ansvarlig: Victor Lotz
//   ------------------------------------

@Composable
fun GameScreen (
    viewModel: ViewModel,
    navController: NavController
) {
    val imageMap by viewModel.imageMap.collectAsState()

    val gameUIState by viewModel.uiState.collectAsState()
    BackButton(navController)
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //LaunchedEffect: AndreasRG:
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
            TitleGameScreen(navController)
            CO2Choices(yellowOption = gameUIState.currentYellowOption, redOption = gameUIState.currentRedOption, imageMap)
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
fun TitleGameScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
            .padding(10.dp)
    ) {
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
    //If statement her: AndreasRG:
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
            when (crownMover) {
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
                        .offset(x = 160.dp, y = 110.dp),
                    contentDescription = null,
                )
                2 -> Image(
                    painter = painterResource(id = R.drawable.highscorecrown2),
                    modifier = Modifier.fillMaxWidth()
                        .height(31.dp)
                        .width(25.dp)
                        .rotate(34F)
                        .offset(x = 205.dp, y = 90.dp),
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
fun CO2Choices(yellowOption: CO2Ting, redOption: CO2Ting, imageMap: Map<String, Bitmap>) {
    val placement = 480.dp;
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(placement)
            .padding(top = placement-350.dp)
    ) {
        // VS
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
                fontSize = 64.sp,
                modifier = Modifier
                    .align (Alignment.Center)
            )
        }
        // CO2 options
        YellowOption(modifier = Modifier
            .align(Alignment.TopEnd), yellowOption, imageMap)
        RedOption(modifier = Modifier
            .align(Alignment.BottomStart), redOption, imageMap)
    }
}

@Composable
fun YellowOption(modifier: Modifier, yellowOption: CO2Ting, imageMap: Map<String, Bitmap>) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp, 0.dp, 0.dp, 100.dp))
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.5f)
            .background(Color(0xFFFFCA58))
    ) {
        // CO2 Image
        val bitmap = imageMap[yellowOption.image]
        if (bitmap != null) {
            Box() {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .border(
                            BorderStroke(8.dp, Color(0xFFFFCA58)),
                            CircleShape
                        ),
                    contentScale = ContentScale.Crop
                )
                // Image text
                Text(
                    yellowOption.name,
                    modifier = Modifier
                        .align (Alignment.BottomCenter)
                        .padding(bottom = 40.dp)
                        .width(150.dp),
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 20.sp,
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
        } else {
            // Show a placeholder or loading animation
            Text("Loading image...",
                fontSize = 16.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                modifier = Modifier
                    .align (Alignment.CenterStart)
                    .padding(start = 5.dp))
        }

        // Icon
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = -30.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Image(
                painter = painterResource(id = R.drawable.triangleicon),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .offset(y = 10.dp)
            )
        }

        Text("udleder ${yellowOption.CO2}kg CO2e",
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
            modifier = Modifier
                .align (Alignment.TopEnd)
                .padding(end = 8.dp, top = 2.dp)
        )
    }
}

@Composable
fun RedOption(modifier: Modifier, redOption: CO2Ting, imageMap: Map<String, Bitmap>) {
    Box(
        modifier
            .clip(shape = RoundedCornerShape(0.dp, 100.dp, 100.dp, 0.dp))
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.5f)
            .background(Color(0xFFFF5858))
    ) {
        Text("udleder ???kg CO2e",
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
            modifier = Modifier
                .align (Alignment.BottomStart)
                .padding(start = 8.dp, bottom = 2.dp)
        )
        // Icon
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = 30.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            // Icon
            Image(
                painter = painterResource(id = R.drawable.staricon),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .offset(y = -10.dp)
            )
        }

        // CO2 image
        val bitmap = imageMap[redOption.image]
        if (bitmap != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            ) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .border(
                            BorderStroke(8.dp, Color(0xFFFF5858)),
                            CircleShape
                        )
                        .align(Alignment.CenterEnd),
                    contentScale = ContentScale.Crop
                )
                // Image text
                Text(
                    redOption.name,
                    modifier = Modifier
                        .align (Alignment.BottomCenter)
                        .padding(bottom = 40.dp)
                        .width(150.dp),
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 20.sp,
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
        } else {
            // Show a placeholder or loading animation
            Text("Loading image...",
                fontSize = 16.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                modifier = Modifier
                    .align (Alignment.CenterEnd)
                    .padding(end = 5.dp)
            )
        }
    }
}

@Composable
fun RedAndYellowButtons(navController: NavController, viewModel: ViewModel) {
    Box(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            // Gul knap
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFCA58)),
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier
                    .height(62.dp)
                    .width(170.dp)
                    .padding(end = 10.dp),
                onClick = {
                    viewModel.chooseYellowOption(navController)
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.triangleicon),
                            contentDescription = null,
                            modifier = Modifier.size(48.dp)
                        )
                        Box(modifier = Modifier
                            .padding(start = 20.dp)) {
                            Text(
                                "Gul",
                                fontSize = 24.sp,
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.bagel_fat_one))
                            )
                        }
                    }
                }
            }
            // Rød knap
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5858)),
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier
                    .height(62.dp)
                    .width(170.dp)
                    .padding(start = 10.dp),
                onClick = {
                    viewModel.chooseRedOption(navController)
                }
            )
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.staricon),
                            contentDescription = null,
                            modifier = Modifier.size(48.dp)
                        )
                        Box(modifier = Modifier
                            .padding(start = 20.dp)) {
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
    }
}
