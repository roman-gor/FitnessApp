package com.gorman.fitnessapp.ui.states

import com.gorman.fitnessapp.domain.models.UserProgress
import com.gorman.fitnessapp.domain.models.WorkoutHistory

sealed class ProgressUiState {
    data class Success(val progressHistory: Pair<List<UserProgress>, List<WorkoutHistory>>): ProgressUiState()
    object Loading: ProgressUiState()
    object Idle: ProgressUiState()
    data class Error(val e: String): ProgressUiState()
}