package com.gorman.fitnessapp.ui.states

sealed class RegisterUiState {
    object Idle: RegisterUiState()
    object Loading: RegisterUiState()
    object Success: RegisterUiState()
    object Logout: RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}