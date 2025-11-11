package com.gorman.fitnessapp.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gorman.fitnessapp.R
import com.gorman.fitnessapp.ui.components.LoadingStub
import com.gorman.fitnessapp.ui.navigation.Screen
import com.gorman.fitnessapp.ui.states.UserCheckState
import com.gorman.fitnessapp.ui.viewmodel.MainViewModel

@Composable
fun PreLoadingScreen(navController: NavController) {
    val mainViewModel: MainViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        mainViewModel.checkUserExisting()
    }
    val uiState by mainViewModel.userCheckState
    val thisRoute = "pre_loading"
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.bg_color))
        )
        when (uiState) {
            is UserCheckState.Error -> {}
            UserCheckState.Idle -> {}
            UserCheckState.Loading -> {
                LoadingStub()
            }
            UserCheckState.NotExists -> {
                navController.navigate(Screen.SetupScreen.Start.route) {
                    popUpTo(thisRoute) {inclusive = true}
                }
            }
            UserCheckState.Success -> {
                navController.navigate("main_screen") {
                    popUpTo(thisRoute) {inclusive = true}
                }
            }
        }
    }
}

