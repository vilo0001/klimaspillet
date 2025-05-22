@file:Suppress("SpellCheckingInspection")

package com.example.klimaspillet.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.klimaspillet.data.repository.UserInfo
import com.example.klimaspillet.ui.ViewModel
import com.example.klimaspillet.ui.screens.HomeScreen
import com.example.klimaspillet.ui.screens.GameScreen
import com.example.klimaspillet.ui.screens.ConnectClassScreen
import com.example.klimaspillet.ui.screens.ResultsScreen


@Composable
fun Navigation(viewModel: ViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.routeHomeScreen, builder = {
        composable(Routes.routeHomeScreen){
            HomeScreen(viewModel, navController)
        }
        composable(Routes.routeGameScreen){
            GameScreen(viewModel, navController)
        }
        composable(Routes.routeConnectClassScreen){
            ConnectClassScreen(viewModel, navController)
        }
        composable(Routes.routeResultsScreen){
            ResultsScreen(viewModel, navController)
        }
    })
}