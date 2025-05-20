package com.example.klimaspillet.ui.screens

import android.graphics.Insets.add
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
import android.os.Build.VERSION.SDK_INT
import android.content.Context
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import java.time.format.TextStyle
import kotlin.Function as Function




//AndreasRG:
@Composable
fun ResultsScreen (navController: NavController) {
    BackgroundResults()
    //HighscoreTopRight()
    Column(modifier = Modifier) {
        ScoreResult()
        GifResult()
        HomeRestartButtons()
    }
}






//AndreasRG:
@Composable
fun BackgroundResults() {
    Image( painter = painterResource(id = R.drawable.background),
        modifier = Modifier
            .fillMaxSize(),
        contentDescription = null,
    )
}

//Hvis vi tilføjer highscore i øverste højre hjørne
// AndreasRG:
/*@Composable
fun
HighscoreTopRight() {
    Row(modifier = Modifier
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.End) {
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
*/

//AndreasRG:
@Composable
fun ScoreResult() {
    if(newHighScore) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 100.dp, 0.dp, 50.dp)) {
            Text(
                "Ny highscore", fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                fontSize = 48.sp,
                color = Color(0xFFFFCC6D),
                style = androidx.compose.ui.text.TextStyle(shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.25f),
                    offset = Offset(8f, 8f)))
            )
            Box() {
                if (currentScore < 10) {
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
                "$currentScore", fontFamily = FontFamily(Font(R.font.bagel_fat_one)),
                fontSize = 120.sp,
                color = Color(0xFFFFCC6D),
                style = androidx.compose.ui.text.TextStyle(shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.25f),
                    offset = Offset(8f, 8f)))
            )
        }
        currentHighscore = currentScore
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 100.dp, 0.dp, 50.dp)) {
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
                "$currentScore",
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
        .padding(20.dp, 0.dp, 15.dp, 0.dp)
        .height(223.dp)
        .width(370.dp)
        .clip(RoundedCornerShape(25.dp))
        ) {
        GifImage()
    }
}

//AndreasRG:
@Composable
fun HomeRestartButtons() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 110.dp, 0.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ResultsScreenButton { HomeIcon() }
        ResultsScreenButton { RestartIcon() }
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
fun ResultsScreenButton(addImage: @Composable () -> Unit) {
    Button(
        onClick = { },
        modifier = Modifier
            .padding(0.dp)
            .width(120.dp)
            .height(120.dp),
        shape = RoundedCornerShape(25.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF64B9FF)
        )
    ) {
        addImage() // Correct placement of the content block
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
var currentHighscore:Int = 0;
//Der skal oprettes en random gif generator efter score }