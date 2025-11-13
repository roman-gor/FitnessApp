package com.gorman.fitnessapp.ui.states

sealed class ProgramUiState {
    object Loading: ProgramUiState()
    object Success: ProgramUiState()
    object Idle: ProgramUiState()
    data class Error(val message: String): ProgramUiState()
}