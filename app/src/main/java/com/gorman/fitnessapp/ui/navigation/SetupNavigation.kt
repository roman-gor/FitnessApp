package com.gorman.fitnessapp.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.ui.screens.setup.AgeScreen
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
            GenderScreen(
                onNextPage = { usersData ->
                    val json = Uri.encode(Json.encodeToString(usersData))
                    navController.navigate("${Screen.SetupScreen.Age}/$json"){
                        launchSingleTop = true
                    } },
                onBackPage = {
                    navController.navigateUp()
                })
        }
        composable(
            route = "age/{usersDataJson}",
            arguments = listOf(
                navArgument("usersDataJson") {
                    type = NavType.StringType
                }
            )) { backStackEntry ->
            val usersDataJson = backStackEntry.arguments?.getString("usersDataJson")
            val usersData = usersDataJson?.let { Json.decodeFromString<UsersData>(it) }
            AgeScreen(
                usersData = usersData,
                onNextPage = { usersData ->
                    val json = Uri.encode(Json.encodeToString(usersData))
                    navController.navigate("${Screen.SetupScreen.Weight}/$json"){
                        launchSingleTop = true
                    } },
                onBackPage = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = "weight/{usersDataJson}",
            arguments = listOf(
                navArgument("usersDataJson") {
                    type = NavType.StringType
                }
            )) { backStackEntry ->
            val usersDataJson = backStackEntry.arguments?.getString("usersDataJson")
            val usersData = usersDataJson?.let { Json.decodeFromString<UsersData>(it) }
        }
    }
}