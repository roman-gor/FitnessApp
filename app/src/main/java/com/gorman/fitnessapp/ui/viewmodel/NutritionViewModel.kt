package com.gorman.fitnessapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.domain.models.Meal
import com.gorman.fitnessapp.domain.usecases.GetMealPlanFromLocalUseCase
import com.gorman.fitnessapp.domain.usecases.GetMealsUseCase
import com.gorman.fitnessapp.logger.AppLogger
import com.gorman.fitnessapp.ui.states.NutritionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutritionViewModel @Inject constructor(
    private val getMealPlanFromLocalUseCase: GetMealPlanFromLocalUseCase,
    private val getMealsUseCase: GetMealsUseCase,
    private val logger: AppLogger
): ViewModel() {
    private val _nutritionUiState = MutableStateFlow<NutritionUiState>(NutritionUiState.Idle)
    val nutritionUiState = _nutritionUiState.asStateFlow()

    private val _mealsState = MutableStateFlow<List<Meal>>(emptyList())
    val mealsState = _mealsState.asStateFlow()

    fun prepareData() {
        viewModelScope.launch {
            resetState()
            _nutritionUiState.value = NutritionUiState.Loading
            try {
                val mealPlan = getMealPlanFromLocalUseCase()
                _nutritionUiState.value = NutritionUiState.Success(mealPlan)
                logger.d("NutritionVM", "Успех. Загрузка планов питания")
            } catch (e: Exception) {
                _nutritionUiState.value = NutritionUiState.Error("${e.message}")
                logger.e("NutritionVM", "Ошибка. Загрузка планов питания")
            } catch (e: IllegalStateException) {
                _nutritionUiState.value = NutritionUiState.Error("${e.message}")
                logger.e("NutritionVM", "Ошибка. Загрузка планов питания")
            }
        }
    }

    fun getMeals() {
        viewModelScope.launch {
            _mealsState.value = getMealsUseCase()
        }
    }

    private fun resetState() {
        _nutritionUiState.value = NutritionUiState.Idle
    }
}