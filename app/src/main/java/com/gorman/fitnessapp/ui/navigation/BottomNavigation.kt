package com.gorman.fitnessapp.ui.navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.domain.models.Article
import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.ui.components.BottomNavigationBar
import com.gorman.fitnessapp.ui.screens.main.ArticleScreen
import com.gorman.fitnessapp.ui.screens.main.HomeScreen
import com.gorman.fitnessapp.ui.screens.main.ProfileScreen
import com.gorman.fitnessapp.ui.screens.main.ResourcesScreen
import com.gorman.fitnessapp.ui.screens.main.SettingsScreen
import com.gorman.fitnessapp.ui.screens.workout.ExerciseByDayScreen
import com.gorman.fitnessapp.ui.screens.workout.GeneratingProgram
import com.gorman.fitnessapp.ui.screens.workout.ProgramByDayScreen
import com.gorman.fitnessapp.ui.screens.workout.WorkoutScreen
import com.gorman.fitnessapp.ui.states.HomeUiState
import com.gorman.fitnessapp.ui.viewmodel.HomeViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun BottomNavigation(navController: NavController) {
    val nestedNavController = rememberNavController()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val state by homeViewModel.homeUiState.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colorResource(R.color.bg_color),
        bottomBar = {
            BottomNavigationBar(
                items = Screen.bItems,
                navController = nestedNavController,
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = nestedNavController,
            startDestination = Screen.BottomScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            Screen.bItems.forEach { bItem ->
                composable(bItem.route) {
                    when (bItem.route) {
                        Screen.BottomScreen.Home.route -> HomeScreen(
                            homeViewModel = homeViewModel,
                            onNutritionClick = {},
                            onProgressClick = {},
                            onWorkoutClick = {
                                nestedNavController.navigate(Screen.GeneralHomeScreen.Workout.route) {
                                    launchSingleTop = true
                                }
                            },
                            onProfileClick = { usersData ->
                                val json = Uri.encode(Json.encodeToString(usersData))
                                nestedNavController.navigate("${Screen.GeneralHomeScreen.Profile.route}/$json") {
                                    launchSingleTop = true
                                }
                            },
                            onNavigateToGenProgram = {
                                navController.navigate("generating_route") {
                                    launchSingleTop = true
                                }
                            },
                            onArticleClick = { article ->
                                val json = Uri.encode(Json.encodeToString(article))
                                nestedNavController.navigate("${Screen.GeneralHomeScreen.Article.route}/$json") {
                                    launchSingleTop = true
                                }
                            })
                        Screen.BottomScreen.Resources.route -> ResourcesScreen(
                            onBackPage = {
                                nestedNavController.navigateUp()
                            },
                            onItemClick = { exercise ->
                                val json = Uri.encode(Json.encodeToString(exercise))
                                nestedNavController.navigate("${Screen.WorkoutScreen.ExerciseByProgram.route}/$json") {
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
            composable(
                route = "${Screen.GeneralHomeScreen.Profile.route}/{usersDataJson}",
                arguments = listOf(
                    navArgument("usersDataJson") {
                        type = NavType.StringType
                    })) { backStackEntry ->
                val usersDataJson = backStackEntry.arguments?.getString("usersDataJson")
                val usersData = usersDataJson?.let { Json.decodeFromString<UsersData>(it) }
                usersData?.let {
                    ProfileScreen(
                        userData = it,
                        onBackPage = {
                            nestedNavController.navigateUp()
                        },
                        onSettingsClick = { user->
                            val json = Uri.encode(Json.encodeToString(user))
                            nestedNavController.navigate("settings/$json")
                        },
                        onNavigateToStart = {
                            navController.navigate(Screen.SetupScreen.Start.route) {
                                popUpTo(0)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
            composable(
                route = "settings/{usersDataJson}",
                arguments = listOf(
                    navArgument("usersDataJson") {
                        type = NavType.StringType
                    })) { backStackEntry ->
                val usersDataJson = backStackEntry.arguments?.getString("usersDataJson")
                val usersData = usersDataJson?.let { Json.decodeFromString<UsersData>(it) }
                usersData?.let {
                    SettingsScreen (
                        usersData = usersData,
                        onBackPage = {nestedNavController.navigateUp()}
                    )
                }
            }
            composable(Screen.GeneralHomeScreen.Workout.route) {
                WorkoutScreen(
                    onBackPage = { nestedNavController.navigate(Screen.BottomScreen.Home.route){
                        popUpTo(0) {inclusive =  true}
                    } },
                    onItemClick = { day->
                        nestedNavController.navigate("${Screen.WorkoutScreen.ProgramByDay.route}/$day")
                    }
                )
            }
            composable(
                route = "${Screen.WorkoutScreen.ProgramByDay.route}/{dayData}",
                arguments = listOf(
                    navArgument("dayData") {
                        type = NavType.StringType
                    })) {backStackEntry ->
                val dayData = backStackEntry.arguments?.getString("dayData")
                dayData?.let {
                    ProgramByDayScreen(
                        onBackPage = {
                            nestedNavController.navigateUp()
                        },
                        day = it,
                        onExerciseProgramClick = { exercise->
                            val json = Uri.encode(Json.encodeToString(exercise))
                            nestedNavController.navigate("${Screen.WorkoutScreen.ExerciseByProgram.route}/$json")
                        }
                    )
                }
            }
            composable(Screen.GeneratingScreen.GenerateProgram.route) {
                GeneratingProgram(
                    onNavigateToProgram = {
                        nestedNavController.navigate(Screen.GeneralHomeScreen.Workout.route) {
                            popUpTo(Screen.BottomScreen.Home.route) { inclusive = false }
                        }
                    }
                )
            }
            composable(
                route = "${Screen.GeneralHomeScreen.Article.route}/{article}",
                arguments = listOf(
                    navArgument("article") {
                        type = NavType.StringType
                    })) { backStackEntry ->
                val articleJson = backStackEntry.arguments?.getString("article")
                val article = articleJson?.let { Json.decodeFromString<Article>(it) }
                article?.let {
                    ArticleScreen(
                        onBackPage = { nestedNavController.navigateUp() },
                        article = article
                    )
                }
            }
            composable(
                route = "${Screen.WorkoutScreen.ExerciseByProgram.route}/{exercise}",
                arguments = listOf(
                    navArgument("exercise") {
                        type = NavType.StringType
                    })) { backStackEntry ->
                val exerciseJson = backStackEntry.arguments?.getString("exercise")
                val exercise = exerciseJson?.let { Json.decodeFromString<Exercise>(it) }
                exercise?.let {
                    ExerciseByDayScreen(
                        onBackPage = {
                            nestedNavController.navigateUp()
                        },
                        exercise = exercise
                    )
                }
            }
        }
    }
}