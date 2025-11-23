package com.gorman.fitnessapp.ui.navigation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.ui.screens.main.PreLoadingScreen
import com.gorman.fitnessapp.ui.screens.onboarding.InfoScreen
import com.gorman.fitnessapp.ui.screens.onboarding.StartOnBoardingScreen
import com.gorman.fitnessapp.ui.screens.setup.ActivityLevelScreen
import com.gorman.fitnessapp.ui.screens.setup.AgeScreen
import com.gorman.fitnessapp.ui.screens.setup.DesiredWeightScreen
import com.gorman.fitnessapp.ui.screens.setup.GenderScreen
import com.gorman.fitnessapp.ui.screens.setup.GoalScreen
import com.gorman.fitnessapp.ui.screens.setup.HeightScreen
import com.gorman.fitnessapp.ui.screens.setup.ProfileSetupScreen
import com.gorman.fitnessapp.ui.screens.setup.SignInScreen
import com.gorman.fitnessapp.ui.screens.setup.WeightScreen
import com.gorman.fitnessapp.ui.screens.setup.WelcomeScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun SetupNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "pre_loading") {
        composable("pre_loading") {
            PreLoadingScreen(navController = navController)
        }
        composable(Screen.SetupScreen.Start.route) {
            StartOnBoardingScreen(navController = navController)
        }
        composable(Screen.SetupScreen.Info.route) {
            InfoScreen {
                navController.navigate(Screen.SetupScreen.SignIn.route) {
                    popUpTo(Screen.SetupScreen.Info.route) { inclusive = true }
                }
            }
        }
        composable(Screen.SetupScreen.SignIn.route) {
            SignInScreen(
                onSignUpScreen = {
                    navController.navigate(Screen.SetupScreen.Welcome.route) },
                onHomePage = {
                    navController.navigate("main_screen") {
                        popUpTo(Screen.SetupScreen.SignIn.route) {inclusive = true}
                        launchSingleTop = true
                    } })
        }
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
        setupComposable(
            currentScreen = Screen.SetupScreen.Age.route,
            nextScreen = Screen.SetupScreen.Weight.route,
            navController = navController,
            content = { usersData, onNextPage, onBackPage ->
                AgeScreen(
                    usersData = usersData,
                    onNextPage = onNextPage,
                    onBackPage = onBackPage
                )
            })
        setupComposable(
            currentScreen = Screen.SetupScreen.Weight.route,
            nextScreen = Screen.SetupScreen.DesiredWeight.route,
            navController = navController,
            content = { usersData, onNextPage, onBackPage ->
                WeightScreen (
                    usersData = usersData,
                    onNextPage = onNextPage,
                    onBackPage = onBackPage
                )
            })
        setupComposable(
            currentScreen = Screen.SetupScreen.DesiredWeight.route,
            nextScreen = Screen.SetupScreen.Height.route,
            navController = navController,
            content = { usersData, onNextPage, onBackPage ->
                DesiredWeightScreen (
                    usersData = usersData,
                    onNextPage = onNextPage,
                    onBackPage = onBackPage
                )
            })
        setupComposable(
            currentScreen = Screen.SetupScreen.Height.route,
            nextScreen = Screen.SetupScreen.Goal.route,
            navController = navController,
            content = { usersData, onNextPage, onBackPage ->
                HeightScreen (
                    usersData = usersData,
                    onNextPage = onNextPage,
                    onBackPage = onBackPage
                )
            })
        setupComposable(
            currentScreen = Screen.SetupScreen.Goal.route,
            nextScreen = Screen.SetupScreen.ActivityLevel.route,
            navController = navController,
            content = { usersData, onNextPage, onBackPage ->
                GoalScreen (
                    usersData = usersData,
                    onNextPage = onNextPage,
                    onBackPage = onBackPage
                )
            })
        setupComposable(
            currentScreen = Screen.SetupScreen.ActivityLevel.route,
            nextScreen = Screen.SetupScreen.ProfileSetup.route,
            navController = navController,
            content = { usersData, onNextPage, onBackPage ->
                ActivityLevelScreen (
                    usersData = usersData,
                    onNextPage = onNextPage,
                    onBackPage = onBackPage
                )
            })
        composable(
            route = "${Screen.SetupScreen.ProfileSetup.route}/{usersDataJson}",
            arguments = listOf(
                navArgument("usersDataJson") {
                    type = NavType.StringType
                }
            )) { backStackEntry ->
            val usersDataJson = backStackEntry.arguments?.getString("usersDataJson")
            val usersData = usersDataJson?.let { Json.decodeFromString<UsersData>(it) }
            Log.d("UsersData", usersData.toString())
            ProfileSetupScreen(
                usersData = usersData,
                onNextPage = {
                    navController.navigate("main_screen") {
                        launchSingleTop = true
                    } },
                onBackPage = {
                    navController.navigateUp()
                }
            )
        }
        composable("main_screen") {
            BottomNavigation(navController)
        }
        composable("generating_route") {
            GeneratingNavigation(
                navController = navController,
                destination = "program"
            )
        }
    }
}

private fun NavGraphBuilder.setupComposable(
    currentScreen: String,
    nextScreen: String,
    content: @Composable (UsersData?, onNext: (UsersData) -> Unit, onBack: () -> Unit) -> Unit,
    navController: NavController
) {
    composable(
        route = "$currentScreen/{usersDataJson}",
        arguments = listOf(navArgument("usersDataJson") { type = NavType.StringType })
    ) { backStackEntry ->
        val usersDataJson = backStackEntry.arguments?.getString("usersDataJson")
        val usersData = usersDataJson?.let { Json.decodeFromString<UsersData>(it) }
        Log.d("UsersData", usersData.toString())

        content(
            usersData,
            { usersData ->
                val json = Uri.encode(Json.encodeToString(usersData))
                navController.navigate("$nextScreen/$json") {
                    launchSingleTop = true
                }
            },
            { navController.navigateUp() }
        )
    }
}