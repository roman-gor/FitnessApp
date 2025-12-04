package com.gorman.fitnessapp.ui.states

import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.Meal

sealed class ResourcesUiState() {
    data class Success(val exercises: List<Exercise>, val meals: List<Meal>): ResourcesUiState()
    object Loading: ResourcesUiState()
    object Idle: ResourcesUiState()
    data class Error(val message: String): ResourcesUiState()
}