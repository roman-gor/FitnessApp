package com.gorman.fitnessapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.ProgramExercise
import com.gorman.fitnessapp.domain.models.UserProgress
import com.gorman.fitnessapp.domain.models.WorkoutHistory
import com.gorman.fitnessapp.domain.usecases.GetExercisesUseCase
import com.gorman.fitnessapp.domain.usecases.GetProgramFromLocalUseCase
import com.gorman.fitnessapp.domain.usecases.GetProgramIdUseCase
import com.gorman.fitnessapp.domain.usecases.GetUserFromLocalUseCase
import com.gorman.fitnessapp.domain.usecases.GetUserIdUseCase
import com.gorman.fitnessapp.domain.usecases.InsertUserProgressUseCase
import com.gorman.fitnessapp.domain.usecases.InsertWorkoutHistoryUseCase
import com.gorman.fitnessapp.logger.AppLogger
import com.gorman.fitnessapp.ui.states.ProgramHistoryState
import com.gorman.fitnessapp.ui.states.ProgramUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class ProgramViewModel @Inject constructor(
    private val logger: AppLogger,
    private val getExercisesUseCase: GetExercisesUseCase,
    private val getProgramFromLocalUseCase: GetProgramFromLocalUseCase,
    private val insertUserProgressUseCase: InsertUserProgressUseCase,
    private val insertWorkoutHistoryUseCase: InsertWorkoutHistoryUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val getProgramIdUseCase: GetProgramIdUseCase,
    private val getUserFromLocalUseCase: GetUserFromLocalUseCase
): ViewModel(){
    private val _programUiState = MutableStateFlow<ProgramUiState>(ProgramUiState.Idle)
    val programUiState = _programUiState.asStateFlow()

    private val _historyInsertState = MutableStateFlow<ProgramHistoryState>(ProgramHistoryState.Idle)
    val historyInsertState = _historyInsertState.asStateFlow()

    private val _exercisesListState = MutableStateFlow<List<Exercise>>(emptyList())
    val exercisesListState = _exercisesListState.asStateFlow()

    private val _programTemplateState = MutableStateFlow<Program?>(null)
    val programTemplateState = _programTemplateState.asStateFlow()

    private val _programExercisesState = MutableStateFlow<List<ProgramExercise>>(emptyList())
    val programExercisesState = _programExercisesState.asStateFlow()

    fun prepareProgramData() {
        _programUiState.value = ProgramUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val exerciseList = getExercisesUseCase()
                val programMap = getProgramFromLocalUseCase()
                _exercisesListState.value = exerciseList
                _programTemplateState.value = programMap.keys.first()
                _programExercisesState.value = programMap.values.first()
                _programUiState.value = ProgramUiState.Success
            } catch (e: IllegalStateException) {
                logger.e("ProgramViewModel", "${e.message}")
                _programUiState.value = ProgramUiState.Error(e.message ?: "Ошибка при извлечении данных")
            } catch (e: Exception) {
                logger.e("ProgramViewModel", "${e.message}")
                _programUiState.value = ProgramUiState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }

    fun getExercisesList() {
        viewModelScope.launch {
            _exercisesListState.value = getExercisesUseCase()
            _historyInsertState.value = ProgramHistoryState.Prepared
            logger.d("Exercises", "${_exercisesListState.value}")
        }
    }

    fun markProgramAsCompleted(programExercises: List<ProgramExercise>) {
        viewModelScope.launch {
            _historyInsertState.value = ProgramHistoryState.Loading
            val duration = programExercises.sumOf { it.durationSec * it.sets } / 60
            val calories = programExercises.sumOf { it.caloriesBurned?.toDouble() ?: 0.0 }.toInt()
            val userWeight = getUserFromLocalUseCase().weight
            val date = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            val userId = getUserIdUseCase() ?: return@launch
            val programId = getProgramIdUseCase()
            val userProgressResult = insertUserProgressUseCase(
                UserProgress(
                    userId = userId,
                    date = date,
                    weight = userWeight,
                    caloriesBurned = calories.toFloat(),
                    durationMinutes = duration
                ))
            val workoutHistoryResult = runCatching { programExercises.forEach { programExercise ->
                insertWorkoutHistoryUseCase(
                    WorkoutHistory(
                        firebaseUserId = userId,
                        exerciseId = programExercise.exerciseId,
                        remoteProgramId = programId,
                        date = date,
                        setsCompleted = programExercise.sets,
                        repsCompleted = programExercise.repetitions
                    )
                ).getOrThrow()
            } }
            if (userProgressResult.isSuccess && workoutHistoryResult.isSuccess) {
                _historyInsertState.value = ProgramHistoryState.Success
                logger.d("InsertUserProgress", "Успешно")
            }
            else {
                logger.e("InsertUserProgress", "Херня")
                val err = userProgressResult.exceptionOrNull() ?: workoutHistoryResult.exceptionOrNull()
                _historyInsertState.value = ProgramHistoryState.Error(err?.message ?: "Ошибка")
            }
        }
    }
}