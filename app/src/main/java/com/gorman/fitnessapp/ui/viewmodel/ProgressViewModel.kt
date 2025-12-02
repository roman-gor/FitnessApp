package com.gorman.fitnessapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.usecases.GetExercisesUseCase
import com.gorman.fitnessapp.domain.usecases.GetUserFromLocalUseCase
import com.gorman.fitnessapp.domain.usecases.GetUserProgressFromLocalUseCase
import com.gorman.fitnessapp.domain.usecases.GetWorkoutHistoryFromLocalUseCase
import com.gorman.fitnessapp.logger.AppLogger
import com.gorman.fitnessapp.ui.states.ProgressUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val logger: AppLogger,
    private val getUserFromLocalUseCase: GetUserFromLocalUseCase,
    private val getUserProgressFromLocalUseCase: GetUserProgressFromLocalUseCase,
    private val getWorkoutHistoryFromLocalUseCase: GetWorkoutHistoryFromLocalUseCase,
    private val getExercisesUseCase: GetExercisesUseCase
): ViewModel() {
    private val _progressState = MutableStateFlow<ProgressUiState>(ProgressUiState.Idle)
    val progressState = _progressState.asStateFlow()

    private val _usersDataState = MutableStateFlow<UsersData?>(null)
    val usersData = _usersDataState.asStateFlow()

    private val _exercisesState = MutableStateFlow<List<Exercise>>(emptyList())
    val exercisesState = _exercisesState.asStateFlow()

    fun prepareData() {
        viewModelScope.launch(Dispatchers.IO) {
            _progressState.value = ProgressUiState.Loading
            try {
                _usersDataState.value = getUserFromLocalUseCase()
                val progressList = getUserProgressFromLocalUseCase()
                val historyList = getWorkoutHistoryFromLocalUseCase()
                _progressState.value = ProgressUiState.Success(Pair(progressList, historyList))
            } catch (e: Exception) {
                logger.e("ProgressViewModel", "Ошибка ${e.message}")
                _progressState.value = ProgressUiState.Error("${e.message}")
            } catch (e: IllegalStateException) {
                logger.e("ProgressViewModel", "Ошибка ${e.message}")
                _progressState.value = ProgressUiState.Error("${e.message}")
            }
        }
    }

    fun getExercises() {
        viewModelScope.launch {
            _exercisesState.value = getExercisesUseCase()
        }
    }
}