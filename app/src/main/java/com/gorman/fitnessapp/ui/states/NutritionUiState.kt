package com.gorman.fitnessapp.ui.states

import com.gorman.fitnessapp.domain.models.MealPlanItem
import com.gorman.fitnessapp.domain.models.MealPlanTemplate

sealed class NutritionUiState {
    data class Success(val mealPlan: Pair<MealPlanTemplate, List<MealPlanItem>>): NutritionUiState()
    object Idle: NutritionUiState()
    object Loading: NutritionUiState()
    data class Error(val e: String): NutritionUiState()
}