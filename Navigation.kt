package com.cristhianbonilla.oraculo

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cristhianbonilla.oraculo.router.Screen
import com.cristhianbonilla.oraculo.screens.DreamMeaningScreen
import com.cristhianbonilla.oraculo.screens.MainScreen


@Composable
fun Navigation(dreamViewModel: DreamViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            MainScreen(navController = navController, dreamViewModel)
        }
        composable(
            route = Screen.DetailScreen.route + "/{dream}",
            arguments = listOf(
                navArgument("dream") {
                    type = NavType.StringType
                    defaultValue = "Tu Sueño es"
                    nullable = true
                }
            )
        ) { entry ->
            DreamMeaningScreen(dream = entry.arguments?.getString("dream"))
        }
    }
}