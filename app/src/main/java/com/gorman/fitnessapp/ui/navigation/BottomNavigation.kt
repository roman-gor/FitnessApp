package com.gorman.fitnessapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.ui.components.BottomNavigationBar
import com.gorman.fitnessapp.ui.screens.main.HomeScreen
import com.gorman.fitnessapp.ui.screens.main.ResourcesScreen

@Composable
fun BottomNavigation() {
    val nestedNavController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colorResource(R.color.bg_color),
        bottomBar = {
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
                            onNutritionClick = {},
                            onProgressClick = {},
                            onWorkoutClick = {},
                            onProfileClick = {})
                        Screen.BottomScreen.Resources.route -> ResourcesScreen()
                    }
                }
            }
        }
    }
}