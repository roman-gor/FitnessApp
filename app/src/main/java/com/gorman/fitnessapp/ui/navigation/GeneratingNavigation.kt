package com.gorman.fitnessapp.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.ui.screens.main.PreGeneratingScreen
import com.gorman.fitnessapp.ui.screens.workout.GeneratingProgram

@Composable
fun GeneratingNavigation(
    navController: NavController,
    destination: String
) {
    val generatingNavController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize()
            .background(colorResource(R.color.bg_color))
    ) { innerPaddings ->
        NavHost(navController = generatingNavController, startDestination = "pre_screen") {
            composable("pre_screen") {
                PreGeneratingScreen(
                    destination = destination,
                    onProgramGenerate = {
                        generatingNavController.navigate(Screen.GeneratingScreen.GenerateProgram.route) {
                            popUpTo("pre_screen") { inclusive = true }
                        }
                    },
                    onMealGenerate = {})
            }
            composable(Screen.GeneratingScreen.GenerateProgram.route) {
                GeneratingProgram(
                    onNavigateToProgram = {
                        navController.navigate("main_screen") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
        Box(modifier = Modifier.padding(innerPaddings)
            .background(color = Color.Transparent))
    }
}