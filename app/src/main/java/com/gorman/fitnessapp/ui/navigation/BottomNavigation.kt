package com.gorman.fitnessapp.ui.navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.ui.components.BottomNavigationBar
import com.gorman.fitnessapp.ui.screens.main.HomeScreen
import com.gorman.fitnessapp.ui.screens.main.ProfileScreen
import com.gorman.fitnessapp.ui.screens.main.ResourcesScreen
import com.gorman.fitnessapp.ui.states.HomeUiState
import com.gorman.fitnessapp.ui.viewmodel.HomeViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun BottomNavigation() {
    val nestedNavController = rememberNavController()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val state by homeViewModel.homeUiState
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colorResource(R.color.bg_color),
        bottomBar = {if (state == HomeUiState.Success )
            BottomNavigationBar(
                items = Screen.bItems,
                navController = nestedNavController
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
                            onWorkoutClick = {},
                            onProfileClick = { usersData ->
                                val json = Uri.encode(Json.encodeToString(usersData))
                                nestedNavController.navigate("${Screen.GeneralHomeScreen.Profile.route}/$json") {
                                    launchSingleTop = true
                                }
                            })
                        Screen.BottomScreen.Resources.route -> ResourcesScreen()
                    }
                }
            }
            composable(
                route = "${Screen.GeneralHomeScreen.Profile.route}/{usersDataJson}",
                arguments = listOf(
                    navArgument("usersDataJson") {
                        type = NavType.StringType
                    }))
            { backStackEntry ->
                val usersDataJson = backStackEntry.arguments?.getString("usersDataJson")
                val usersData = usersDataJson?.let { Json.decodeFromString<UsersData>(it) }
                usersData?.let {
                    ProfileScreen(
                        userData = it,
                        onBackPage = {
                            nestedNavController.navigateUp()
                        },
                        onSettingsClick = {}
                    )
                }
            }
        }
    }
}