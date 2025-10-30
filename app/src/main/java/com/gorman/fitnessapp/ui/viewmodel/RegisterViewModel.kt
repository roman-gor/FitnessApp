package com.gorman.fitnessapp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.data.datasource.local.dao.ExerciseDao
import com.gorman.fitnessapp.data.datasource.local.dao.MealDao
import com.gorman.fitnessapp.data.datasource.local.dao.ProgramDao
import com.gorman.fitnessapp.data.datasource.local.dao.ProgramExerciseDao
import com.gorman.fitnessapp.data.datasource.local.dao.UserProgramDao
import com.gorman.fitnessapp.data.datasource.local.dao.UserProgressDao
import com.gorman.fitnessapp.data.datasource.local.dao.UsersDataDao
import com.gorman.fitnessapp.domain.models.UserProgress
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.SupabaseRepository
import com.gorman.fitnessapp.domain.usecases.GenerateAndSyncMealPlansUseCase
import com.gorman.fitnessapp.domain.usecases.GenerateAndSyncProgramUseCase
import com.gorman.fitnessapp.domain.usecases.GetAndSyncMealPlansUseCase
import com.gorman.fitnessapp.domain.usecases.GetAndSyncUserProgramsUseCase
import com.gorman.fitnessapp.domain.usecases.GetExercisesUseCase
import com.gorman.fitnessapp.domain.usecases.GetMealsUseCase
import com.gorman.fitnessapp.domain.usecases.GetUserIdUseCase
import com.gorman.fitnessapp.domain.usecases.InsertUserProgressLocalAndRemoteUseCase
import com.gorman.fitnessapp.domain.usecases.SaveNewUserUseCase
import com.gorman.fitnessapp.domain.usecases.SetUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val generateAndSyncProgramUseCase: GenerateAndSyncProgramUseCase,
    private val generateAndSyncMealPlansUseCase: GenerateAndSyncMealPlansUseCase,
    private val saveNewUserUseCase: SaveNewUserUseCase,
    private val getExercisesUseCase: GetExercisesUseCase,
    private val getMealsUseCase: GetMealsUseCase,
    private val daoM: UserProgressDao,
    private val daoU: UsersDataDao,
    private val getAndSyncUserProgramsUseCase: GetAndSyncUserProgramsUseCase,
    private val getAndSyncMealPlansUseCase: GetAndSyncMealPlansUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val insertUserProgressLocalAndRemoteUseCase: InsertUserProgressLocalAndRemoteUseCase
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
//                _json.value = generateAndSyncProgramUseCase(testUserData, 1)
//                _json.value = generateAndSyncMealPlansUseCase(
//                    usersData = testUserData,
//                    goal = testUserData.goal!!,
//                    exceptionProducts = listOf("Молоко")
//                )
//                getExercisesUseCase()
//                getMealsUseCase()
//                getAndSyncUserProgramsUseCase(testUserData.supabaseId)
//                getAndSyncMealPlansUseCase(testUserData.supabaseId)
                val userId = getUserIdUseCase()
//                userId?.let {
//                    insertUserProgressLocalAndRemoteUseCase(UserProgress(
//                        remoteUserId = it,
//                        date = 1750000000,
//                        weight = 120f
//                    ))
//                }
                saveNewUserUseCase(testUserData)
                val list = daoM.getUserProgress()
                val pList = daoU.getUser()
                Log.d("ViewModelListRoom", "${daoU.getUsersCount()}")
                Log.d("ViewModelListRoom", "$pList")
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