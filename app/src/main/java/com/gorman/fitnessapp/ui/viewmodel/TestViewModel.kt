package com.gorman.fitnessapp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.data.datasource.local.dao.ProgramExerciseDao
import com.gorman.fitnessapp.data.datasource.local.dao.UsersDataDao
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import com.gorman.fitnessapp.domain.usecases.GenerateAndSyncMealPlansUseCase
import com.gorman.fitnessapp.domain.usecases.GenerateAndSyncProgramUseCase
import com.gorman.fitnessapp.domain.usecases.GetAndSyncMealPlansUseCase
import com.gorman.fitnessapp.domain.usecases.GetAndSyncUserProgramsUseCase
import com.gorman.fitnessapp.domain.usecases.GetExercisesUseCase
import com.gorman.fitnessapp.domain.usecases.GetMealsUseCase
import com.gorman.fitnessapp.domain.usecases.GetUserIdUseCase
import com.gorman.fitnessapp.domain.usecases.InsertUserProgressLocalAndRemoteUseCase
import com.gorman.fitnessapp.domain.usecases.SaveNewUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val generateAndSyncProgramUseCase: GenerateAndSyncProgramUseCase,
    private val generateAndSyncMealPlansUseCase: GenerateAndSyncMealPlansUseCase,
    private val firebaseRepository: FirebaseRepository,
    private val saveNewUserUseCase: SaveNewUserUseCase,
    private val dao: ProgramExerciseDao,
    private val daoM: UsersDataDao,
    private val getAndSyncUserProgramsUseCase: GetAndSyncUserProgramsUseCase,
    private val getMealsUseCase: GetMealsUseCase,
    private val getExercisesUseCase: GetExercisesUseCase,
    private val getAndSyncMealPlansUseCase: GetAndSyncMealPlansUseCase,
    private val insertUserProgressLocalAndRemoteUseCase: InsertUserProgressLocalAndRemoteUseCase,
    private val getUserIdUseCase: GetUserIdUseCase
) : ViewModel(){
    private val _usersState: MutableState<List<UsersData>> = mutableStateOf(emptyList())
    val usersState: State<List<UsersData>> = _usersState
    private val _json: MutableState<String> = mutableStateOf("")
    val json: State<String> = _json
    val testUserData = UsersData(
        name = "Горбачёв",
        email = "romangorbachev2006@gmail.com",
        goal = "Набор мышечной массы (Гипертрофия)",
        experienceLevel = "Средний",
        weight = 75f,
        desiredWeight = 80f,
        activityLevel = "Умеренный (3-4 тренировки в неделю)"
    )
    fun prompt() {
        viewModelScope.launch {
            try {
                Log.d("PromptCall", "Начинаем вызов Gemini...")
//                getUserIdUseCase()?.let {
//                    _json.value = generateAndSyncProgramUseCase(testUserData.copy(firebaseId = it), 1)
//                    _json.value = generateAndSyncMealPlansUseCase(
//                        usersData = testUserData.copy(firebaseId = it),
//                        goal = testUserData.goal!!,
//                        exceptionProducts = listOf("Молоко")
//                    )
//                }
//                getMealsUseCase()
                getExercisesUseCase()
                getAndSyncUserProgramsUseCase("-OdorLNj2815IFDbXNMz")
//                getAndSyncMealPlansUseCase("0")
//                val userId = getUserIdUseCase()
//                userId?.let {
//                    insertUserProgressLocalAndRemoteUseCase(
//                        UserProgress(
//                            userId = it,
//                            date = 1750000000,
//                            weight = 120f
//                        )
//                    )
//                }
                val list = dao.getList()
                val pList = daoM.getUser()
                Log.d("ViewModelListRoom", "${dao.getProgramsExerciseCount()}")
                Log.d("ViewModelListRoom", "$list")
                Log.d("PromptCall", "Вызов Gemini завершен успешно.")
            } catch (e: Exception) {
                Log.e("PROMPT_CRASH", "Критическая ошибка: ${e.message}", e)
            }
        }
    }

    fun addUser(user: UsersData) {
        viewModelScope.launch {
            saveNewUserUseCase(user)
        }
    }
}