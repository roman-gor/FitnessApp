package com.gorman.fitnessapp.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.ProgramExercise
import com.gorman.fitnessapp.domain.usecases.GenerateAndSyncProgramUseCase
import com.gorman.fitnessapp.domain.usecases.GetAndSyncUserProgramsUseCase
import com.gorman.fitnessapp.domain.usecases.GetExercisesUseCase
import com.gorman.fitnessapp.domain.usecases.GetProgramFromLocalUseCase
import com.gorman.fitnessapp.domain.usecases.GetUserIdUseCase
import com.gorman.fitnessapp.logger.AppLogger
import com.gorman.fitnessapp.ui.states.ProgramUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgramViewModel @Inject constructor(
    private val logger: AppLogger,
    private val getExercisesUseCase: GetExercisesUseCase,
    private val getProgramFromLocalUseCase: GetProgramFromLocalUseCase
): ViewModel(){
    private val _programUiState = mutableStateOf<ProgramUiState>(ProgramUiState.Idle)
    val programUiState: State<ProgramUiState> = _programUiState

    private val _exercisesListState = mutableStateOf<List<Exercise>>(emptyList())
    val exercisesListState: State<List<Exercise>> = _exercisesListState

    private val _programTemplateState = mutableStateOf<Program?>(null)
    val programTemplateState: State<Program?> = _programTemplateState

    private val _programExercisesState = mutableStateOf<List<ProgramExercise>>(emptyList())
    val programExercisesState: State<List<ProgramExercise>> = _programExercisesState

    fun prepareProgramData() {
        _programUiState.value = ProgramUiState.Loading
        viewModelScope.launch {
            try {
                val exerciseList = getExercisesUseCase()
                val programMap = getProgramFromLocalUseCase()
                exerciseList?.let { _exercisesListState.value = it }
                _programTemplateState.value = programMap.keys.first()
                _programExercisesState.value = programMap.values.first()
                _programUiState.value = ProgramUiState.Success
            } catch (e: IllegalStateException) {
                logger.d("ProgramViewModel", "${e.message}")
                _programUiState.value = ProgramUiState.Error(e.message ?: "Ошибка при извлечении данных")
            } catch (e: Exception) {
                logger.d("ProgramViewModel", "${e.message}")
                _programUiState.value = ProgramUiState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }
}