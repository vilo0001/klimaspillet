@file:Suppress("SpellCheckingInspection")

package com.example.klimaspillet.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.klimaspillet.ui.ViewModel
import com.example.klimaspillet.ui.screens.HomeScreen
import com.example.klimaspillet.ui.screens.GameScreen
import com.example.klimaspillet.ui.screens.ConnectClassScreen
import com.example.klimaspillet.ui.screens.ResultsScreen


@Composable
fun Navigation(viewModel: ViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.routeConnectClassScreen, builder = {
        composable(Routes.routeHomeScreen){
            HomeScreen(navController)
        }
        composable(Routes.routeGameScreen){
            GameScreen(viewModel, navController)
        }
        composable(Routes.routeConnectClassScreen){
            ConnectClassScreen(navController)
        }
        composable(Routes.routeResultsScreen){
            ResultsScreen(navController)
        }
    })
}