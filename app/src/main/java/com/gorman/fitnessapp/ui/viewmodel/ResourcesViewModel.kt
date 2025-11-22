package com.gorman.fitnessapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.domain.usecases.GetExercisesUseCase
import com.gorman.fitnessapp.logger.AppLogger
import com.gorman.fitnessapp.ui.states.ExercisesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResourcesViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesUseCase,
    private val logger: AppLogger
): ViewModel() {
    private val _exercisesState = MutableStateFlow<ExercisesUiState>(ExercisesUiState.Idle)
    val exercisesState: StateFlow<ExercisesUiState> = _exercisesState.asStateFlow()

    fun loadExercises() {
        viewModelScope.launch {
            _exercisesState.value = ExercisesUiState.Loading
            try {
                val exercisesList = getExercisesUseCase()
                _exercisesState.value = ExercisesUiState.Success(exercisesList)
            } catch (e: IllegalStateException) {
                logger.e("ExercisesViewModel", "${e.message}")
                _exercisesState.value = ExercisesUiState.Error("${e.message}")
            } catch (e: Exception) {
                logger.e("ExercisesViewModel", "${e.message}")
                _exercisesState.value = ExercisesUiState.Error("${e.message}")
            }
        }
    }
}