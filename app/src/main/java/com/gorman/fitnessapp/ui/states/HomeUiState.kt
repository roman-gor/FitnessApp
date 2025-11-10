package com.gorman.fitnessapp.ui.states

sealed class HomeUiState {
    object Loading: HomeUiState()
    object Success: HomeUiState()
    object Idle: HomeUiState()
    data class Error(val message: String): HomeUiState()
}