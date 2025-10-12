package com.gorman.fitnessapp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.data.datasource.ai.GeminiGenerator
import com.gorman.fitnessapp.data.mapper.toEntity
import com.gorman.fitnessapp.data.models.UsersDataEntity
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val geminiGenerator: GeminiGenerator
) : ViewModel(){
    private val _usersState: MutableState<List<UsersData>> = mutableStateOf(emptyList())
    val usersState: MutableState<List<UsersData>> = _usersState
    val testUserData = UsersDataEntity(
        goal = "Набор мышечной массы (Гипертрофия)",
        experienceLevel = "Средний",
        weight = 75f,
        desiredWeight = 80f,
        activityLevel = "Умеренный (3-4 тренировки в неделю)"
    )
    val availableExercises = mapOf(
        1 to "Жим штанги лежа",              // ex_1
        2 to "Жим гантелей на наклонной скамье", // ex_2
        3 to "Сведения рук в кроссовере",       // ex_3
        4 to "Приседания со штангой",          // ex_4
        5 to "Жим ногами в тренажере",         // ex_5
        6 to "Разгибание ног в тренажере",     // ex_6
        7 to "Становая тяга (классика)",      // ex_7
        8 to "Тяга штанги в наклоне",         // ex_8
        9 to "Подтягивания широким хватом",    // ex_9
        10 to "Армейский жим стоя",          // ex_10
        11 to "Разведение гантелей в стороны (махи)",// ex_11
        12 to "Тяга штанги к подбородку",    // ex_12
        13 to "Подъем штанги на бицепс стоя",  // ex_13
        14 to "Сгибание рук с гантелями (Молотки)",// ex_14
        15 to "Французский жим лежа",        // ex_15
        16 to "Разгибание рук на блоке (трицепс)",// ex_16
        17 to "Подъемы ног в висе",           // ex_17
        18 to "Планка (секунды)",            // ex_18
        19 to "Гиперэкстензия",              // ex_19
        20 to "Сгибание ног лежа в тренажере" // ex_20
    )

    fun prompt() {
        viewModelScope.launch {
            try {
                Log.d("PromptCall", "Начинаем вызов Gemini...")
                geminiGenerator.generateWorkoutProgram(
                    userData = testUserData,
                    availableExercises = availableExercises
                )
                Log.d("PromptCall", "Вызов Gemini завершен успешно.")
            } catch (e: Exception) {
                Log.e("PROMPT_CRASH", "Критическая ошибка: ${e.message}", e)
            }
        }
    }
    fun getAllUsers() {
        viewModelScope.launch {
            _usersState.value = databaseRepository.getAllUsers()
        }
    }

    fun addUser(user: UsersData) {
        viewModelScope.launch {
            databaseRepository.addUser(user)
        }
    }
}