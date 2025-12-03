package com.gorman.fitnessapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.domain.usecases.GenerateAndSyncMealPlansUseCase
import com.gorman.fitnessapp.domain.usecases.GenerateAndSyncProgramUseCase
import com.gorman.fitnessapp.domain.usecases.GetMealsIdUseCase
import com.gorman.fitnessapp.domain.usecases.GetProgramIdUseCase
import com.gorman.fitnessapp.domain.usecases.GetUserFromLocalUseCase
import com.gorman.fitnessapp.logger.AppLogger
import com.gorman.fitnessapp.ui.states.GeneratingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeneratingViewModel @Inject constructor(
    private val logger: AppLogger,
    private val generateAndSyncProgramUseCase: GenerateAndSyncProgramUseCase,
    private val generateAndSyncMealPlansUseCase: GenerateAndSyncMealPlansUseCase,
    private val getProgramIdUseCase: GetProgramIdUseCase,
    private val getMealsIdUseCase: GetMealsIdUseCase,
    private val getUserFromLocalUseCase: GetUserFromLocalUseCase
): ViewModel() {
    private val _genUiState = MutableStateFlow<GeneratingUiState>(GeneratingUiState.Idle)
    val genUiState: StateFlow<GeneratingUiState> = _genUiState

    private var dietState: String = ""
    private var caloriesState: String = ""
    private var allergiesState: List<String> = emptyList()

    init {
        viewModelScope.launch {
            _genUiState.value = GeneratingUiState.Idle
            if (getProgramIdUseCase().isNotEmpty())
                _genUiState.value = GeneratingUiState.ProgramIsExist
            if (getMealsIdUseCase().isNotEmpty())
                _genUiState.value = GeneratingUiState.MealsIsExist
        }
    }

    fun generateProgram() {
        _genUiState.value = GeneratingUiState.Loading
        viewModelScope.launch {
            try {
                val user = getUserFromLocalUseCase()
                generateAndSyncProgramUseCase(user)
                _genUiState.value = GeneratingUiState.Success
            } catch (e: IllegalStateException) {
                logger.e("GeneratingProgramVM", "${e.message}")
                _genUiState.value = GeneratingUiState.Error("${e.message}")
            } catch (e: Exception) {
                logger.e("GeneratingProgramVM", "${e.message}")
                _genUiState.value = GeneratingUiState.Error("${e.message}")
            }
        }
    }

    fun generateMeals(
        dietaryPreferences: String,
        calories: String,
        exceptionProducts: List<String>
    ) {
        dietState = dietaryPreferences
        caloriesState = calories
        allergiesState = exceptionProducts
        executeMealGeneration()
    }

    fun onTryAgainClicked() {
        executeMealGeneration()
    }

    private fun executeMealGeneration() {
        _genUiState.value = GeneratingUiState.Loading
        viewModelScope.launch {
            try {
                val user = getUserFromLocalUseCase()
                generateAndSyncMealPlansUseCase(
                    userData = user,
                    dietaryPreferences = dietState,
                    calories = caloriesState,
                    exceptionProducts = allergiesState,
                )
                _genUiState.value = GeneratingUiState.Success
            } catch (e: IllegalStateException) {
                logger.e("GeneratingMealVM", "${e.message}")
                _genUiState.value = GeneratingUiState.Error("${e.message}")
            } catch (e: Exception) {
                logger.e("GeneratingMealVM", "${e.message}")
                _genUiState.value = GeneratingUiState.Error("${e.message}")
            }
        }
    }
}