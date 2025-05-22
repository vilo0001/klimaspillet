@file:Suppress("SpellCheckingInspection")

package com.example.klimaspillet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.klimaspillet.R
import com.example.klimaspillet.navigation.Routes
import com.example.klimaspillet.ui.ViewModel

//Andreas B
@Composable
fun HomeScreen (
    viewModel: ViewModel = viewModel(),
    navController: NavController
) {
    val gameUIState by viewModel.uiState.collectAsState()
    //Background()
    InfoIconWithDialog()
    Column {
        KlimaSpillet()
        NoClassLeaderboard(navController)
        PlayButton(navController)
    }
}

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
                color = Color.White
            )
        }
    }
}

//Andreas B
@Composable
fun NoClassLeaderboard(navController: NavController) {
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

            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            )
            {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        // Magnus: forslag til rettelse, det her er den præcis røde fra figma | containerColor = Color(255, 100, 100, 255) |
                        containerColor = Color(255, 100, 100, 255)
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
}

//Andreas B
@Composable
fun PlayButton (navController: NavController) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 85.dp)
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


@Composable
fun InfoIconWithDialog() {
    var showDialog by remember { mutableStateOf(false) }


    Icon(
        painter = painterResource(id = R.drawable.info),
        contentDescription = "InfoIcon",
        modifier = Modifier
            .padding(16.dp)
            .size(48.dp)
            .clickable { showDialog = true },
        tint = Color.White
    )

    // Dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = "Klimaspillet instruktion:",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily =  FontFamily(Font(R.font.bagel_fat_one)),
                        fontWeight = FontWeight.Bold
                    )
                    )

            },
            text = {
                Text("Du får hvert runde stillet et spørgsmål hvor 2 Co2 udledene aktiviteter bliver vist. Dit formål er at gætte hvilken af de to der udleder MEST Co2. \n\n Gætter du rigtigt fortsætter du videre ind i spillet. Gætter du forkert ryger du ud.\n\n Formålet er at få så høj en highscore som muligt, samt at få den højeste score i klassen.",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                        color = Color(0, 0, 0, 255)
                    ))
            },
            confirmButton = {
            }
        )
    }
}
