package com.gorman.fitnessapp.ui.navigation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.ui.screens.setup.AgeScreen
import com.gorman.fitnessapp.ui.screens.setup.DesiredWeightScreen
import com.gorman.fitnessapp.ui.screens.setup.GenderScreen
import com.gorman.fitnessapp.ui.screens.setup.GoalScreen
import com.gorman.fitnessapp.ui.screens.setup.HeightScreen
import com.gorman.fitnessapp.ui.screens.setup.WeightScreen
import com.gorman.fitnessapp.ui.screens.setup.WelcomeScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun SetupNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "welcome") {
        composable(Screen.SetupScreen.Welcome.route) {
            WelcomeScreen {
                navController.navigate(Screen.SetupScreen.Gender.route)
            }
        }
        composable(Screen.SetupScreen.Gender.route) {
            GenderScreen(
                onNextPage = { usersData ->
                    val json = Uri.encode(Json.encodeToString(usersData))
                    navController.navigate("${Screen.SetupScreen.Age.route}/$json"){
                        launchSingleTop = true
                    } },
                onBackPage = {
                    navController.navigateUp()
                })
        }
        composable(
            route = "${Screen.SetupScreen.Age.route}/{usersDataJson}",
            arguments = listOf(
                navArgument("usersDataJson") {
                    type = NavType.StringType
                }
            )) { backStackEntry ->
            val usersDataJson = backStackEntry.arguments?.getString("usersDataJson")
            val usersData = usersDataJson?.let { Json.decodeFromString<UsersData>(it) }
            Log.d("UsersData", usersData.toString())
            AgeScreen(
                usersData = usersData,
                onNextPage = { usersData ->
                    val json = Uri.encode(Json.encodeToString(usersData))
                    navController.navigate("${Screen.SetupScreen.Weight.route}/$json"){
                        launchSingleTop = true
                    } },
                onBackPage = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = "${Screen.SetupScreen.Weight.route}/{usersDataJson}",
            arguments = listOf(
                navArgument("usersDataJson") {
                    type = NavType.StringType
                }
            )) { backStackEntry ->
            val usersDataJson = backStackEntry.arguments?.getString("usersDataJson")
            val usersData = usersDataJson?.let { Json.decodeFromString<UsersData>(it) }
            Log.d("UsersData", usersData.toString())
            WeightScreen(
                usersData = usersData,
                onNextPage = { usersData ->
                    val json = Uri.encode(Json.encodeToString(usersData))
                    navController.navigate("${Screen.SetupScreen.DesiredWeight.route}/$json") {
                        launchSingleTop = true
                    } },
                onBackPage = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = "${Screen.SetupScreen.DesiredWeight.route}/{usersDataJson}",
            arguments = listOf(
                navArgument("usersDataJson") {
                    type = NavType.StringType
                }
            )) { backStackEntry ->
            val usersDataJson = backStackEntry.arguments?.getString("usersDataJson")
            val usersData = usersDataJson?.let { Json.decodeFromString<UsersData>(it) }
            Log.d("UsersData", usersData.toString())
            DesiredWeightScreen(
                usersData = usersData,
                onNextPage = { usersData ->
                    val json = Uri.encode(Json.encodeToString(usersData))
                    navController.navigate("${Screen.SetupScreen.Height.route}/$json") {
                        launchSingleTop = true
                    } },
                onBackPage = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = "${Screen.SetupScreen.Height.route}/{usersDataJson}",
            arguments = listOf(
                navArgument("usersDataJson") {
                    type = NavType.StringType
                }
            )) { backStackEntry ->
            val usersDataJson = backStackEntry.arguments?.getString("usersDataJson")
            val usersData = usersDataJson?.let { Json.decodeFromString<UsersData>(it) }
            Log.d("UsersData", usersData.toString())
            HeightScreen(
                usersData = usersData,
                onNextPage = { usersData ->
                    val json = Uri.encode(Json.encodeToString(usersData))
                    navController.navigate("${Screen.SetupScreen.Goal.route}/$json") {
                        launchSingleTop = true
                    } },
                onBackPage = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = "${Screen.SetupScreen.Goal.route}/{usersDataJson}",
            arguments = listOf(
                navArgument("usersDataJson") {
                    type = NavType.StringType
                }
            )) { backStackEntry ->
            val usersDataJson = backStackEntry.arguments?.getString("usersDataJson")
            val usersData = usersDataJson?.let { Json.decodeFromString<UsersData>(it) }
            Log.d("UsersData", usersData.toString())
            GoalScreen (
                usersData = usersData,
                onNextPage = { usersData ->
                    val json = Uri.encode(Json.encodeToString(usersData))
                    navController.navigate("${Screen.SetupScreen.ActivityLevel.route}/$json") {
                        launchSingleTop = true
                    } },
                onBackPage = {
                    navController.navigateUp()
                }
            )
        }
    }
}