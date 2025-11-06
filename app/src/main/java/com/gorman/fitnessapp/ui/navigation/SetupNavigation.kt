package com.gorman.fitnessapp.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gorman.fitnessapp.ui.screens.setup.GenderScreen
import com.gorman.fitnessapp.ui.screens.setup.WelcomeScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun SetupNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen {
                navController.navigate(Screen.SetupScreen.Gender)
            }
        }
        composable("gender") {
            GenderScreen { usersData ->
                val json = Uri.encode(Json.encodeToString(usersData))
                navController.navigate("${Screen.SetupScreen.Gender}$json")
            }
        }
    }
}