package com.gorman.fitnessapp.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.usecases.GenerateAndSyncMealPlansUseCase
import com.gorman.fitnessapp.domain.usecases.GenerateAndSyncProgramUseCase
import com.gorman.fitnessapp.domain.usecases.GetProgramFromLocalUseCase
import com.gorman.fitnessapp.domain.usecases.GetUserFromLocalUseCase
import com.gorman.fitnessapp.logger.AppLogger
import com.gorman.fitnessapp.ui.states.GeneratingUiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class GeneratingViewModel @Inject constructor(
    private val logger: AppLogger,
    private val generateAndSyncProgramUseCase: GenerateAndSyncProgramUseCase,
    private val generateAndSyncMealPlansUseCase: GenerateAndSyncMealPlansUseCase,
    private val getProgramFromLocalUseCase: GetProgramFromLocalUseCase,
    private val getUserFromLocalUseCase: GetUserFromLocalUseCase
): ViewModel() {

    private val _genUiState = mutableStateOf<GeneratingUiState>(GeneratingUiState.Idle)
    val genUiState: State<GeneratingUiState> = _genUiState

    private val _generatedProgramState = mutableStateOf<Program?>(null)
    val generatedProgramState: State<Program?> = _generatedProgramState

    fun generateProgram() {
        _genUiState.value = GeneratingUiState.Loading
        viewModelScope.launch {
            try {
                val user = getUserFromLocalUseCase()
                generateAndSyncProgramUseCase(user)
                _generatedProgramState.value = getProgramFromLocalUseCase().keys.first()
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