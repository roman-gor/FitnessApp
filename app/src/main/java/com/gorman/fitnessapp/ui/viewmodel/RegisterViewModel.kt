package com.gorman.fitnessapp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.data.datasource.local.dao.MealPlanItemDao
import com.gorman.fitnessapp.data.datasource.local.dao.MealPlanTemplateDao
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import com.gorman.fitnessapp.domain.usecases.GenerateAndSyncMealPlans
import com.gorman.fitnessapp.domain.usecases.GenerateAndSyncProgramUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val generateAndSyncProgramUseCase: GenerateAndSyncProgramUseCase,
    private val generateAndSyncMealPlans: GenerateAndSyncMealPlans,
    private val firebaseRepository: FirebaseRepository,
    private val dao: MealPlanTemplateDao,
    private val daoM: MealPlanItemDao
) : ViewModel(){
    private val _usersState: MutableState<List<UsersData>> = mutableStateOf(emptyList())
    val usersState: State<List<UsersData>> = _usersState
    private val _json: MutableState<String> = mutableStateOf("")
    val json: State<String> = _json
    val testUserData = UsersData(
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

    val availableMeals = mapOf(
        1 to "Овсяная каша на воде с ягодами и орехами",
        2 to "Гречневая каша с отварной куриной грудкой",
        3 to "Творог 5% жирности с медом и бананом",
        4 to "Омлет из 3-х яиц со шпинатом и помидорами",
        5 to "Запеченная треска с брокколи на пару",
        6 to "Стейк из индейки на гриле с бурым рисом",
        7 to "Большой овощной салат (огурцы, помидоры, перец, зелень) с оливковым маслом",
        8 to "Куриный суп с лапшой и овощами",
        9 to "Говядина, тушеная с черносливом и морковью",
        10 to "Чечевичный суп-пюре",
        11 to "Протеиновый коктейль (сывороточный изолят)",
        12 to "Греческий йогурт без добавок",
        13 to "Цельнозерновые хлебцы с авокадо и слабосоленым лососем",
        14 to "Яблоко",
        15 to "Горсть миндаля (30г)"
    )

    fun prompt() {
        viewModelScope.launch {
            try {
                Log.d("PromptCall", "Начинаем вызов Gemini...")
                _json.value = generateAndSyncProgramUseCase(testUserData, 1)
//                _json.value = generateAndSyncMealPlans(
//                    usersData = testUserData,
//                    goal = testUserData.goal!!,
//                    exceptionProducts = listOf("Молоко")
//                )
                val list = dao.getMealPlanTemplates()
                val pList = daoM.getMealPlanItems()
                Log.d("ViewModelListRoom", "$list")
                Log.d("ViewModelListRoom", "$pList")
                Log.d("PromptCall", "Вызов Gemini завершен успешно.")
            } catch (e: Exception) {
                Log.e("PROMPT_CRASH", "Критическая ошибка: ${e.message}", e)
            }
        }
    }

    fun addUser(user: UsersData) {
        viewModelScope.launch {
            databaseRepository.addUser(user)
        }
    }
}