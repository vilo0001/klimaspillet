@file:Suppress("SpellCheckingInspection")

package com.example.klimaspillet.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.klimaspillet.R
import com.example.klimaspillet.navigation.Routes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shadow

//Andreas B
@Composable
fun HomeScreen (navController: NavController) {
    //Background()
    HighscoreTopRight()
    Column {
        KlimaSpillet()
        NoClassLeaderboard(navController)
        PlayButton(navController)
    }
}


//Nye værdier der skal bruges
val classCode:String = "HA1000";
var className:String = "7.A";
var hasClass:Boolean = true;
var thirdPlaceName:String = "Andreas B";
var secondPlaceName:String = "Magnus G";
var firstPlaceName:String = "Victor L";
var scoreThirdPlace:Int = 9;
var scoreSecondPlace:Int = 11;
var scoreFirstPlace:Int = 15;




//Andreas B
@Composable
fun KlimaSpillet () {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 300.dp)
            .padding(bottom = 20.dp)
    ) {
        Box {
            Text(
                text ="Klimaspillet",
                fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                fontSize = 65.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                style = androidx.compose.ui.text.TextStyle(shadow = Shadow(//AndreasRG
                    color = Color.Black.copy(alpha = 0.25f)))
            )
        }
    }
}

//Andreas B
@Composable
fun NoClassLeaderboard(navController: NavController) {
    if(hasClass) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .width(360.dp)
                    .height(260.dp)
                    .background(Color.White.copy(alpha = 0.4f), shape = RoundedCornerShape(16.dp))
                    .padding(30.dp)
            ) {
                Text(
                    text = "Leaderboard",
                    fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                    color = Color.White,
                    fontSize = 30.sp
                )
                Text(
                    text = "Du er ikke en del af en klasse",
                    fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                    color = Color.White,
                    fontSize = 20.sp
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        ),
                        modifier = Modifier
                            .shadow(16.dp, shape = RoundedCornerShape(16.dp))
                            .height(50.dp),
                        onClick = {
                            // Victor Lotz
                            navController.navigate(Routes.routeConnectClassScreen)
                        },
                    ) {
                        Text(
                            text = "Tilslut klasse",
                            fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    } else {//AndreasRG:
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 50.dp),
            horizontalArrangement = Arrangement.Center) {
            Text(
                text = "$className Leaderboard",
                fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                color = Color.White,
                fontSize = 36.sp,
                style = androidx.compose.ui.text.TextStyle(shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.25f)))
            )
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Box(modifier = Modifier
                .offset(y = 45.dp)) {
                Box(
                    modifier = Modifier
                        .width(55.dp)
                        .height(55.dp)
                        .clip(RoundedCornerShape(27.5.dp))
                        .background(Color(0xFFD9D9D9).copy(alpha = 0.5f))
                        .offset(0.dp, -3.dp),
                    contentAlignment = Alignment.Center
                )
                {
                    Text(
                        text = scoreSecondPlace.toString(),
                        fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                        color = Color(0xFFCECECE),
                        fontSize = 40.sp
                    )
                }
            }
            Box(modifier = Modifier
                .width(55.dp)) {
                Box(
                    modifier = Modifier
                        .width(55.dp)
                        .height(55.dp)
                        .clip(RoundedCornerShape(27.5.dp))
                        .background(Color(0xFFD9D9D9).copy(alpha = 0.5f))
                        .offset(0.dp, -3.dp),
                    contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = scoreFirstPlace.toString(),
                            fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                            color = Color(0xFFF9DA5F),
                            fontSize = 40.sp
                        )
                    }
                }
                    Image(
                        painter = painterResource(id = R.drawable.highscorecrown),
                        modifier = Modifier.fillMaxWidth()
                            .height(31.dp)
                            .width(25.dp)
                            .offset(x = 0.dp, y = -17.dp),
                        contentDescription = null)
            }
            Box(modifier = Modifier
                .offset(y = 60.dp)) {
                Box(
                    modifier = Modifier
                        .width(55.dp)
                        .height(55.dp)
                        .clip(RoundedCornerShape(27.5.dp))
                        .background(Color(0xFFD9D9D9).copy(alpha = 0.5f))
                        .offset(0.dp, -3.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = scoreThirdPlace.toString(),
                        fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                        color = Color(0xFFFF9E4A),
                        fontSize = 40.sp
                    )
                }
            }
        }
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom) {
            Box (modifier = Modifier
                .height(67.dp)
                .width(117.dp)
                .clip(RoundedCornerShape(10.dp, 10.dp))
                .background(Color(0xFFCECECE)),
                contentAlignment = Alignment.TopCenter) {
                Text(text = secondPlaceName,
                    fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                    color = Color.White,
                    fontSize = 24.sp)
            }
            Box (modifier = Modifier
                .height(107.dp)
                .width(117.dp)
                .clip(RoundedCornerShape(10.dp, 10.dp))
                .background(Color(0xFFFFCC6D)),
                contentAlignment = Alignment.TopCenter) {
                Text(text = firstPlaceName,
                    fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                    color = Color.White,
                    fontSize = 24.sp)
            }
            Box (modifier = Modifier
                .height(53.dp)
                .width(117.dp)
                .clip(RoundedCornerShape(10.dp, 10.dp))
                .background(Color(0xFFFF9E4A)),
                contentAlignment = Alignment.TopCenter) {
                Text(text = thirdPlaceName,
                    fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                    color = Color.White,
                    fontSize = 24.sp)
            }
        }
    }
}

//Andreas B
@Composable
fun PlayButton (navController: NavController) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 45.dp)
    ) {
        Button(
            modifier = Modifier
                .width(125.dp)
                .height(125.dp)
                .shadow(16.dp, shape = RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF64B9FF)
            ),
            onClick = {
                // Victor Lotz
                navController.navigate(Routes.routeGameScreen)
            }) {
            Icon(
                painter = painterResource(id = R.drawable.playbutton),
                contentDescription = "Playbutton",
                modifier = Modifier.size(48.dp)
            )
        }
    }
}