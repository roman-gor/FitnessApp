package com.gorman.fitnessapp.ui.states

sealed class ProgramUiState {
    object Loading: ProgramUiState()
    object Success: ProgramUiState()
    object Idle: ProgramUiState()
    data class Error(val message: String): ProgramUiState()
}

sealed class ProgramHistoryState {
    object Loading: ProgramHistoryState()
    object Success: ProgramHistoryState()
    object Idle: ProgramHistoryState()
    object Prepared: ProgramHistoryState()
    data class Error(val message: String): ProgramHistoryState()
}