package com.gorman.fitnessapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.domain.usecases.GetExercisesUseCase
import com.gorman.fitnessapp.domain.usecases.GetMealsUseCase
import com.gorman.fitnessapp.logger.AppLogger
import com.gorman.fitnessapp.ui.states.ResourcesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResourcesViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesUseCase,
    private val getMealsUseCase: GetMealsUseCase,
    private val logger: AppLogger
): ViewModel() {
    private val _resourcesState = MutableStateFlow<ResourcesUiState>(ResourcesUiState.Idle)
    val resourcesState: StateFlow<ResourcesUiState> = _resourcesState.asStateFlow()

    fun loadExercises() {
        viewModelScope.launch {
            _resourcesState.value = ResourcesUiState.Loading
            try {
                val exercisesList = getExercisesUseCase()
                val mealsList = getMealsUseCase()
                _resourcesState.value = ResourcesUiState.Success(exercisesList, mealsList)
            } catch (e: IllegalStateException) {
                logger.e("ExercisesViewModel", "${e.message}")
                _resourcesState.value = ResourcesUiState.Error("${e.message}")
            } catch (e: Exception) {
                logger.e("ExercisesViewModel", "${e.message}")
                _resourcesState.value = ResourcesUiState.Error("${e.message}")
            }
        }
    }
}