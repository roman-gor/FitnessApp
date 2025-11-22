package com.gorman.fitnessapp.ui.states

import com.gorman.fitnessapp.domain.models.Exercise

sealed class ExercisesUiState() {
    data class Success(val list: List<Exercise>): ExercisesUiState()
    object Loading: ExercisesUiState()
    object Idle: ExercisesUiState()
    data class Error(val message: String): ExercisesUiState()
}