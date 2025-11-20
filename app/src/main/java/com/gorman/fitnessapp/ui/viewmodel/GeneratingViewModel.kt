package com.gorman.fitnessapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.usecases.GenerateAndSyncMealPlansUseCase
import com.gorman.fitnessapp.domain.usecases.GenerateAndSyncProgramUseCase
import com.gorman.fitnessapp.domain.usecases.GetProgramFromLocalUseCase
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
    private val getProgramIdUseCase: GetProgramIdUseCase,
    private val getUserFromLocalUseCase: GetUserFromLocalUseCase
): ViewModel() {
    private val _genUiState = MutableStateFlow<GeneratingUiState>(GeneratingUiState.Idle)
    val genUiState: StateFlow<GeneratingUiState> = _genUiState

    init {
        viewModelScope.launch {
            if (getProgramIdUseCase().isNotEmpty())
                _genUiState.value = GeneratingUiState.IsExist
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
}