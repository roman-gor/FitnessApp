package com.gorman.fitnessapp.ui.states

sealed class GeneratingUiState {
    object Loading: GeneratingUiState()
    object Success: GeneratingUiState()
    object IsExist: GeneratingUiState()
    data class Error(val message: String): GeneratingUiState()
    object Idle: GeneratingUiState()
}
