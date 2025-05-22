@file:Suppress("SpellCheckingInspection")

package com.example.klimaspillet.ui.screens

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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





//AndreasRG:
@Composable
fun ResultsScreen (
    viewModel: ViewModel,
    navController: NavController
) {
    val gameUIState by viewModel.uiState.collectAsState()
    //PrivateBackground()
    if (!viewModel.newHighscoreBoolean) { HighscoreTopRight(gameUIState.highscore) }
    Column(modifier = Modifier) {
        ScoreResult(gameUIState.score, viewModel.newHighscoreBoolean)
        GifResult()
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
            .padding(bottom = 45.dp)) {
            HomeRestartButtons(viewModel, navController)
        }
    }
}








// AndreasRG:
@Composable
fun
HighscoreTopRight() {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Column(
                modifier = Modifier
                    .padding(end = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.highscorecrown),
                    modifier = Modifier
                        .height(33.dp)
                        .width(25.dp)
                        .offset(y = 17.dp),
                    contentDescription = null,
                )
                Text(
                    "$currentHighscore", fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                    fontSize = 32.sp,
                    color = Color(0xFFFFCC6D),
                    style = androidx.compose.ui.text.TextStyle(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.25f),
                            offset = Offset(4f, 4f)
                        )
                    )
                )
            }
        }
    }

//AndreasRG:
// Victor Lotz, score fra viewmodel
@Composable
fun ScoreResult(score: Int, newHighscore: Boolean) {
    println("Score: $score, new highscore?: $newHighscore")
    // Det er vel fint nok, at denne simple logik ikke er i viewmodel?
    if(newHighscore) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp)) {
            Text(
                "Ny highscore", fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                fontSize = 48.sp,
                color = Color(0xFFFFCC6D),
                style = androidx.compose.ui.text.TextStyle(shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.25f),
                    offset = Offset(8f, 8f)))
            )
            Box() {
                if (score < 10) {
                    Image(
                        painter = painterResource(id = R.drawable.highscorecrown),
                        modifier = Modifier.fillMaxWidth()
                            .height(31.dp)
                            .width(25.dp)
                            .rotate(34F)
                            .offset(x = 60.dp, y = 30.dp),
                        contentDescription = null,
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.highscorecrown),
                        modifier = Modifier.fillMaxWidth()
                            .height(31.dp)
                            .width(25.dp)
                            .rotate(34F)
                            .offset(x = 80.dp, y = 15.dp),
                        contentDescription = null,
                    )
                }
            }
            Text(
                "$score", fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                fontSize = 120.sp,
                color = Color(0xFFFFCC6D),
                style = androidx.compose.ui.text.TextStyle(shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.25f),
                    offset = Offset(8f, 8f)))
            )
        }
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp)) {
            Text(
                "Din score", fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                fontSize = 48.sp,
                color = Color(0xFFFFFFFF),
                style = androidx.compose.ui.text.TextStyle(shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.25f),
                    offset = Offset(8f, 8f)))
            )
            Spacer(modifier = Modifier
                .height(31.dp))
            Text(
                "$score",
                fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                fontSize = 120.sp,
                color = Color(0xFFFFFFFF),
                style = androidx.compose.ui.text.TextStyle(shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.25f),
                    offset = Offset(8f, 8f)))
            )
        }
    }
}

//AndreasRG:
@Composable
fun GifResult() {
    Box(modifier = Modifier
        .padding(start = 20.dp, end = 15.dp)
        .height(223.dp)
        .width(370.dp)
        .clip(RoundedCornerShape(25.dp))
        ) {
        GifImage()
    }
}

//AndreasRG:
@Composable
fun HomeRestartButtons(viewModel: ViewModel, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 110.dp, 0.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ResultsScreenButton(
            addImage = { HomeIcon() },
            navController = navController,
            navigateAction = {
                viewModel.resetGame()
                navController.navigate(Routes.routeHomeScreen)
            }
        )

        ResultsScreenButton(
            addImage = { RestartIcon() },
            navController = navController,
            navigateAction = {
                viewModel.resetGame()
                navController.navigate(Routes.routeGameScreen)
            }
        )
    }
}





//Functions udenfor indsatte Composables med logik
// AndreasRG:
@Composable
fun GifImage() {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context).components {
        if (SDK_INT >= 28) { add(ImageDecoderDecoder.Factory())
        } else {
            add(GifDecoder.Factory())
        }
    } .build()
    AsyncImage( model = R.drawable.angrybrodestroypc, // Tilføj random gif efter score her
    imageLoader = imageLoader, modifier = Modifier.fillMaxWidth(),
        contentDescription = null,)
}

//AndreasRG:
@Composable
fun ResultsScreenButton(
    addImage: @Composable () -> Unit,
    navController: NavController,
    navigateAction: () -> Unit
) {
    Button(
        onClick = { navigateAction() },
        modifier = Modifier
            .padding(0.dp)
            .width(120.dp)
            .height(120.dp),
        shape = RoundedCornerShape(25.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF64B9FF)
        )
    ) {
        addImage()
    }
}


//AndreasRG:
@Composable
fun HomeIcon() {
    Image( painter = painterResource(id = R.drawable.homeicon),
        modifier = Modifier.fillMaxWidth()
            .height(67.dp)
            .width(67.dp),
        contentDescription = null,)
}

//AndreasRG:
@Composable
fun RestartIcon() {
    Image( painter = painterResource(id = R.drawable.restarticon),
        modifier = Modifier.fillMaxWidth()
            .height(67.dp)
            .width(67.dp),
        contentDescription = null,
    )
}
/*
-
-
-
-
-
-
-
-
-
*/
//Temporary testing values, skal forbindes med Game logik
var currentScore:Int = 0;
var newHighScore:Boolean = true;
var currentHighscore:Int = 10

/*
fun playVideo(context: Context) {
    val mediaPlayer = MediaPlayer.create(context, R.raw.baggrundanimation)
    mediaPlayer.start()
}
 */

//Der skal oprettes en random gif generator efter score }