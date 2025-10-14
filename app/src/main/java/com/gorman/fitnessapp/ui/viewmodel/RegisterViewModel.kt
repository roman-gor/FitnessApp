package com.gorman.fitnessapp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gorman.fitnessapp.data.datasource.ai.GeminiGenerator
import com.gorman.fitnessapp.data.datasource.ai.dto.ProgramDto
import com.gorman.fitnessapp.data.mapper.toEntity
import com.gorman.fitnessapp.data.models.UsersDataEntity
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val geminiGenerator: GeminiGenerator
) : ViewModel(){
    private val _usersState: MutableState<List<UsersData>> = mutableStateOf(emptyList())
    val usersState: State<List<UsersData>> = _usersState

    private val _json: MutableState<String> = mutableStateOf("")
    val json: State<String> = _json

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
                _json.value = geminiGenerator.generateWorkoutProgram(
                    userData = testUserData,
                    availableExercises = availableExercises
                )
                val programDtos: List<ProgramDto> = Json.decodeFromString("""[
  {
    "program_id": "prog_mass_ppl_01",
    "name": "Классический сплит: Тяни-Толкай-Ноги",
    "description": "Эффективная 4-недельная программа для набора мышечной массы, построенная по принципу разделения тренировок на 'толкающие', 'тянущие' движения и день ног. Идеально подходит для проработки каждой мышечной группы раз в неделю с высоким объемом.",
    "muscleGroup": "Тяни-Толкай-Ноги",
    "goalType": "mass_gain",
    "program_exercise": [
      {
        "detail_id": "ppl_d_001",
        "exerciseId": "1",
        "order": 1,
        "dayOfWeek": "Понедельник",
        "sets": 4,
        "repetitions": 10,
        "durationSec": 0,
        "caloriesBurned": 120
      },
      {
        "detail_id": "ppl_d_002",
        "exerciseId": "2",
        "order": 2,
        "dayOfWeek": "Понедельник",
        "sets": 3,
        "repetitions": 12,
        "durationSec": 0,
        "caloriesBurned": 90
      },
      {
        "detail_id": "ppl_d_003",
        "exerciseId": "10",
        "order": 3,
        "dayOfWeek": "Понедельник",
        "sets": 4,
        "repetitions": 10,
        "durationSec": 0,
        "caloriesBurned": 100
      },
      {
        "detail_id": "ppl_d_004",
        "exerciseId": "11",
        "order": 4,
        "dayOfWeek": "Понедельник",
        "sets": 3,
        "repetitions": 15,
        "durationSec": 0,
        "caloriesBurned": 60
      },
      {
        "detail_id": "ppl_d_005",
        "exerciseId": "15",
        "order": 5,
        "dayOfWeek": "Понедельник",
        "sets": 3,
        "repetitions": 12,
        "durationSec": 0,
        "caloriesBurned": 70
      },
      {
        "detail_id": "ppl_d_006",
        "exerciseId": "16",
        "order": 6,
        "dayOfWeek": "Понедельник",
        "sets": 3,
        "repetitions": 15,
        "durationSec": 0,
        "caloriesBurned": 50
      },
      {
        "detail_id": "ppl_d_007",
        "exerciseId": "9",
        "order": 1,
        "dayOfWeek": "Среда",
        "sets": 4,
        "repetitions": 10,
        "durationSec": 0,
        "caloriesBurned": 110
      },
      {
        "detail_id": "ppl_d_008",
        "exerciseId": "8",
        "order": 2,
        "dayOfWeek": "Среда",
        "sets": 4,
        "repetitions": 10,
        "durationSec": 0,
        "caloriesBurned": 130
      },
      {
        "detail_id": "ppl_d_009",
        "exerciseId": "19",
        "order": 3,
        "dayOfWeek": "Среда",
        "sets": 3,
        "repetitions": 15,
        "durationSec": 0,
        "caloriesBurned": 60
      },
      {
        "detail_id": "ppl_d_010",
        "exerciseId": "13",
        "order": 4,
        "dayOfWeek": "Среда",
        "sets": 4,
        "repetitions": 12,
        "durationSec": 0,
        "caloriesBurned": 80
      },
      {
        "detail_id": "ppl_d_011",
        "exerciseId": "14",
        "order": 5,
        "dayOfWeek": "Среда",
        "sets": 3,
        "repetitions": 12,
        "durationSec": 0,
        "caloriesBurned": 65
      },
      {
        "detail_id": "ppl_d_012",
        "exerciseId": "17",
        "order": 6,
        "dayOfWeek": "Среда",
        "sets": 3,
        "repetitions": 20,
        "durationSec": 0,
        "caloriesBurned": 50
      },
      {
        "detail_id": "ppl_d_013",
        "exerciseId": "4",
        "order": 1,
        "dayOfWeek": "Пятница",
        "sets": 4,
        "repetitions": 10,
        "durationSec": 0,
        "caloriesBurned": 150
      },
      {
        "detail_id": "ppl_d_014",
        "exerciseId": "5",
        "order": 2,
        "dayOfWeek": "Пятница",
        "sets": 3,
        "repetitions": 15,
        "durationSec": 0,
        "caloriesBurned": 110
      },
      {
        "detail_id": "ppl_d_015",
        "exerciseId": "20",
        "order": 3,
        "dayOfWeek": "Пятница",
        "sets": 4,
        "repetitions": 12,
        "durationSec": 0,
        "caloriesBurned": 80
      },
      {
        "detail_id": "ppl_d_016",
        "exerciseId": "6",
        "order": 4,
        "dayOfWeek": "Пятница",
        "sets": 3,
        "repetitions": 15,
        "durationSec": 0,
        "caloriesBurned": 70
      },
      {
        "detail_id": "ppl_d_017",
        "exerciseId": "18",
        "order": 5,
        "dayOfWeek": "Пятница",
        "sets": 3,
        "repetitions": 1,
        "durationSec": 60,
        "caloriesBurned": 45
      }
    ]
  },
  {
    "program_id": "prog_mass_ul_02",
    "name": "Сплит: Верх/Низ",
    "description": "Эта программа на 4 недели увеличивает частоту проработки мышечных групп, разделяя тренировки на дни верха и низа тела. Чередование силовых и объемных дней для верха тела стимулирует гипертрофию под разными углами.",
    "muscleGroup": "Верх-Низ",
    "goalType": "mass_gain",
    "program_exercise": [
      {
        "detail_id": "ul_d_001",
        "exerciseId": "1",
        "order": 1,
        "dayOfWeek": "Понедельник",
        "sets": 4,
        "repetitions": 8,
        "durationSec": 0,
        "caloriesBurned": 110
      },
      {
        "detail_id": "ul_d_002",
        "exerciseId": "8",
        "order": 2,
        "dayOfWeek": "Понедельник",
        "sets": 4,
        "repetitions": 8,
        "durationSec": 0,
        "caloriesBurned": 120
      },
      {
        "detail_id": "ul_d_003",
        "exerciseId": "10",
        "order": 3,
        "dayOfWeek": "Понедельник",
        "sets": 3,
        "repetitions": 10,
        "durationSec": 0,
        "caloriesBurned": 90
      },
      {
        "detail_id": "ul_d_004",
        "exerciseId": "9",
        "order": 4,
        "dayOfWeek": "Понедельник",
        "sets": 3,
        "repetitions": 12,
        "durationSec": 0,
        "caloriesBurned": 95
      },
      {
        "detail_id": "ul_d_005",
        "exerciseId": "13",
        "order": 5,
        "dayOfWeek": "Понедельник",
        "sets": 3,
        "repetitions": 12,
        "durationSec": 0,
        "caloriesBurned": 70
      },
      {
        "detail_id": "ul_d_006",
        "exerciseId": "16",
        "order": 6,
        "dayOfWeek": "Понедельник",
        "sets": 3,
        "repetitions": 12,
        "durationSec": 0,
        "caloriesBurned": 60
      },
      {
        "detail_id": "ul_d_007",
        "exerciseId": "4",
        "order": 1,
        "dayOfWeek": "Среда",
        "sets": 4,
        "repetitions": 10,
        "durationSec": 0,
        "caloriesBurned": 150
      },
      {
        "detail_id": "ul_d_008",
        "exerciseId": "20",
        "order": 2,
        "dayOfWeek": "Среда",
        "sets": 4,
        "repetitions": 12,
        "durationSec": 0,
        "caloriesBurned": 85
      },
      {
        "detail_id": "ul_d_009",
        "exerciseId": "5",
        "order": 3,
        "dayOfWeek": "Среда",
        "sets": 3,
        "repetitions": 15,
        "durationSec": 0,
        "caloriesBurned": 110
      },
      {
        "detail_id": "ul_d_010",
        "exerciseId": "19",
        "order": 4,
        "dayOfWeek": "Среда",
        "sets": 3,
        "repetitions": 15,
        "durationSec": 0,
        "caloriesBurned": 60
      },
      {
        "detail_id": "ul_d_011",
        "exerciseId": "17",
        "order": 5,
        "dayOfWeek": "Среда",
        "sets": 3,
        "repetitions": 20,
        "durationSec": 0,
        "caloriesBurned": 50
      },
      {
        "detail_id": "ul_d_012",
        "exerciseId": "2",
        "order": 1,
        "dayOfWeek": "Пятница",
        "sets": 4,
        "repetitions": 12,
        "durationSec": 0,
        "caloriesBurned": 100
      },
      {
        "detail_id": "ul_d_013",
        "exerciseId": "9",
        "order": 2,
        "dayOfWeek": "Пятница",
        "sets": 4,
        "repetitions": 15,
        "durationSec": 0,
        "caloriesBurned": 115
      },
      {
        "detail_id": "ul_d_014",
        "exerciseId": "11",
        "order": 3,
        "dayOfWeek": "Пятница",
        "sets": 4,
        "repetitions": 15,
        "durationSec": 0,
        "caloriesBurned": 75
      },
      {
        "detail_id": "ul_d_015",
        "exerciseId": "3",
        "order": 4,
        "dayOfWeek": "Пятница",
        "sets": 3,
        "repetitions": 15,
        "durationSec": 0,
        "caloriesBurned": 65
      },
      {
        "detail_id": "ul_d_016",
        "exerciseId": "14",
        "order": 5,
        "dayOfWeek": "Пятница",
        "sets": 3,
        "repetitions": 15,
        "durationSec": 0,
        "caloriesBurned": 70
      },
      {
        "detail_id": "ul_d_017",
        "exerciseId": "15",
        "order": 6,
        "dayOfWeek": "Пятница",
        "sets": 3,
        "repetitions": 15,
        "durationSec": 0,
        "caloriesBurned": 75
      }
    ]
  },
  {
    "program_id": "prog_mass_str_03",
    "name": "Фуллбоди с силовым акцентом",
    "description": "4-недельный цикл, нацеленный на увеличение силовых показателей в базовых упражнениях (присед, жим, тяга), что является мощным стимулом для роста мышечной массы. Каждая тренировка прорабатывает все тело с акцентом на одно ключевое движение.",
    "muscleGroup": "Фуллбоди",
    "goalType": "mass_gain",
    "program_exercise": [
      {
        "detail_id": "str_d_001",
        "exerciseId": "4",
        "order": 1,
        "dayOfWeek": "Понедельник",
        "sets": 5,
        "repetitions": 8,
        "durationSec": 0,
        "caloriesBurned": 180
      },
      {
        "detail_id": "str_d_002",
        "exerciseId": "2",
        "order": 2,
        "dayOfWeek": "Понедельник",
        "sets": 4,
        "repetitions": 12,
        "durationSec": 0,
        "caloriesBurned": 100
      },
      {
        "detail_id": "str_d_003",
        "exerciseId": "8",
        "order": 3,
        "dayOfWeek": "Понедельник",
        "sets": 4,
        "repetitions": 12,
        "durationSec": 0,
        "caloriesBurned": 110
      },
      {
        "detail_id": "str_d_004",
        "exerciseId": "11",
        "order": 4,
        "dayOfWeek": "Понедельник",
        "sets": 3,
        "repetitions": 15,
        "durationSec": 0,
        "caloriesBurned": 60
      },
      {
        "detail_id": "str_d_005",
        "exerciseId": "16",
        "order": 5,
        "dayOfWeek": "Понедельник",
        "sets": 3,
        "repetitions": 15,
        "durationSec": 0,
        "caloriesBurned": 55
      },
      {
        "detail_id": "str_d_006",
        "exerciseId": "1",
        "order": 1,
        "dayOfWeek": "Среда",
        "sets": 5,
        "repetitions": 8,
        "durationSec": 0,
        "caloriesBurned": 150
      },
      {
        "detail_id": "str_d_007",
        "exerciseId": "20",
        "order": 2,
        "dayOfWeek": "Среда",
        "sets": 4,
        "repetitions": 12,
        "durationSec": 0,
        "caloriesBurned": 85
      },
      {
        "detail_id": "str_d_008",
        "exerciseId": "9",
        "order": 3,
        "dayOfWeek": "Среда",
        "sets": 4,
        "repetitions": 12,
        "durationSec": 0,
        "caloriesBurned": 110
      },
      {
        "detail_id": "str_d_009",
        "exerciseId": "13",
        "order": 4,
        "dayOfWeek": "Среда",
        "sets": 3,
        "repetitions": 12,
        "durationSec": 0,
        "caloriesBurned": 70
      },
      {
        "detail_id": "str_d_010",
        "exerciseId": "18",
        "order": 5,
        "dayOfWeek": "Среда",
        "sets": 3,
        "repetitions": 1,
        "durationSec": 75,
        "caloriesBurned": 55
      },
      {
        "detail_id": "str_d_011",
        "exerciseId": "7",
        "order": 1,
        "dayOfWeek": "Пятница",
        "sets": 4,
        "repetitions": 8,
        "durationSec": 0,
        "caloriesBurned": 200
      },
      {
        "detail_id": "str_d_012",
        "exerciseId": "10",
        "order": 2,
        "dayOfWeek": "Пятница",
        "sets": 4,
        "repetitions": 10,
        "durationSec": 0,
        "caloriesBurned": 100
      },
      {
        "detail_id": "str_d_013",
        "exerciseId": "5",
        "order": 3,
        "dayOfWeek": "Пятница",
        "sets": 4,
        "repetitions": 15,
        "durationSec": 0,
        "caloriesBurned": 120
      },
      {
        "detail_id": "str_d_014",
        "exerciseId": "15",
        "order": 4,
        "dayOfWeek": "Пятница",
        "sets": 3,
        "repetitions": 12,
        "durationSec": 0,
        "caloriesBurned": 70
      },
      {
        "detail_id": "str_d_015",
        "exerciseId": "17",
        "order": 5,
        "dayOfWeek": "Пятница",
        "sets": 3,
        "repetitions": 20,
        "durationSec": 0,
        "caloriesBurned": 50
      }
    ]
  }
]
                """
                )
                for (programDto in programDtos) {
                    Log.d("PromptCall", "Программа: $programDto")
                }
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