package com.example.klimaspillet.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.klimaspillet.ui.screens.HomeScreen
import com.example.klimaspillet.ui.screens.GameScreen
import com.example.klimaspillet.ui.screens.ConnectClassScreen


@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.routeHomeScreen, builder = {
        composable(Routes.routeGameScreen){
            GameScreen(navController)
        }
        composable(Routes.routeHomeScreen){
            HomeScreen(navController)
        }
        composable(Routes.routeConnectClassScreen){
            ConnectClassScreen(navController)
        }
    })
}