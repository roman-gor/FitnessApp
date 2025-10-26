package com.gorman.fitnessapp.data.repository

import android.util.Log
import com.gorman.fitnessapp.data.datasource.ai.GeminiGenerator
import com.gorman.fitnessapp.data.datasource.ai.dto.MealPlanDto
import com.gorman.fitnessapp.data.datasource.ai.dto.ProgramDto
import com.gorman.fitnessapp.data.mapper.toDomain
import com.gorman.fitnessapp.domain.models.MealPlan
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.AiRepository
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AiRepositoryImpl @Inject constructor(
    private val generator: GeminiGenerator
): AiRepository {
    override suspend fun generatePrograms(usersData: UsersData, availableExercises: Map<Int?, String>?): List<Program> {
//        val response = generator.generateWorkoutProgram(userData = usersData, availableExercises = availableExercises)
//            .trimIndent()
//            .removePrefix("```json")
//            .removeSuffix("```")
//        Log.d("Json", response)
        val testResponse = """
            [
                                                                                                      {
                                                                                                        "program_id": "prog_mass_ppl_001",
                                                                                                        "name": "Сила и Масса: Тяни-Толкай-Ноги",
                                                                                                        "description": "4-недельная программа, основанная на классическом сплите \"Тяни-Толкай-Ноги\". Понедельник (Тяни) - спина и бицепс. Среда (Толкай) - грудь, плечи и трицепс. Пятница (Ноги) - полный фокус на ноги и кор. Идеально для проработки каждой группы мышц раз в неделю с максимальным объемом для стимуляции гипертрофии.",
                                                                                                        "muscleGroup": "Сплит",
                                                                                                        "goalType": "mass_gain",
                                                                                                        "program_exercise": [
                                                                                                          {
                                                                                                            "detail_id": "p1_d001",
                                                                                                            "exerciseId": 4,
                                                                                                            "order": 1,
                                                                                                            "dayOfWeek": "Понедельник",
                                                                                                            "sets": 4,
                                                                                                            "repetitions": 10,
                                                                                                            "durationSec": 60,
                                                                                                            "caloriesBurned": 50
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p1_d002",
                                                                                                            "exerciseId": 6,
                                                                                                            "order": 2,
                                                                                                            "dayOfWeek": "Понедельник",
                                                                                                            "sets": 4,
                                                                                                            "repetitions": 10,
                                                                                                            "durationSec": 45,
                                                                                                            "caloriesBurned": 60
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p1_d003",
                                                                                                            "exerciseId": 17,
                                                                                                            "order": 3,
                                                                                                            "dayOfWeek": "Понедельник",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 12,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 40
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p1_d004",
                                                                                                            "exerciseId": 13,
                                                                                                            "order": 4,
                                                                                                            "dayOfWeek": "Понедельник",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 12,
                                                                                                            "durationSec": 45,
                                                                                                            "caloriesBurned": 30
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p1_d005",
                                                                                                            "exerciseId": 23,
                                                                                                            "order": 5,
                                                                                                            "dayOfWeek": "Понедельник",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 12,
                                                                                                            "durationSec": 45,
                                                                                                            "caloriesBurned": 25
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p1_d006",
                                                                                                            "exerciseId": 1,
                                                                                                            "order": 1,
                                                                                                            "dayOfWeek": "Среда",
                                                                                                            "sets": 4,
                                                                                                            "repetitions": 10,
                                                                                                            "durationSec": 45,
                                                                                                            "caloriesBurned": 65
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p1_d007",
                                                                                                            "exerciseId": 10,
                                                                                                            "order": 2,
                                                                                                            "dayOfWeek": "Среда",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 12,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 45
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p1_d008",
                                                                                                            "exerciseId": 5,
                                                                                                            "order": 3,
                                                                                                            "dayOfWeek": "Среда",
                                                                                                            "sets": 4,
                                                                                                            "repetitions": 10,
                                                                                                            "durationSec": 45,
                                                                                                            "caloriesBurned": 55
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p1_d009",
                                                                                                            "exerciseId": 11,
                                                                                                            "order": 4,
                                                                                                            "dayOfWeek": "Среда",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 15,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 30
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p1_d010",
                                                                                                            "exerciseId": 7,
                                                                                                            "order": 5,
                                                                                                            "dayOfWeek": "Среда",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 12,
                                                                                                            "durationSec": 45,
                                                                                                            "caloriesBurned": 40
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p1_d011",
                                                                                                            "exerciseId": 2,
                                                                                                            "order": 1,
                                                                                                            "dayOfWeek": "Пятница",
                                                                                                            "sets": 4,
                                                                                                            "repetitions": 10,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 70
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p1_d012",
                                                                                                            "exerciseId": 18,
                                                                                                            "order": 2,
                                                                                                            "dayOfWeek": "Пятница",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 12,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 50
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p1_d013",
                                                                                                            "exerciseId": 14,
                                                                                                            "order": 3,
                                                                                                            "dayOfWeek": "Пятница",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 12,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 45
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p1_d014",
                                                                                                            "exerciseId": 19,
                                                                                                            "order": 4,
                                                                                                            "dayOfWeek": "Пятница",
                                                                                                            "sets": 4,
                                                                                                            "repetitions": 20,
"durationSec": 45,
                                                                                                            "caloriesBurned": 35
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p1_d015",
                                                                                                            "exerciseId": 22,
                                                                                                            "order": 5,
                                                                                                            "dayOfWeek": "Пятница",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 20,
                                                                                                            "durationSec": 60,
                                                                                                            "caloriesBurned": 25
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p1_d016",
                                                                                                            "exerciseId": 20,
                                                                                                            "order": 6,
                                                                                                            "dayOfWeek": "Пятница",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 1,
                                                                                                            "durationSec": 60,
                                                                                                            "caloriesBurned": 15
                                                                                                          }
                                                                                                        ]
                                                                                                      },
                                                                                                      {
                                                                                                        "program_id": "prog_mass_ul_002",
                                                                                                        "name": "Гипертрофия: Верх/Низ",
                                                                                                        "description": "4-недельная программа по сплиту \"Верх/Низ\". Этот подход позволяет прорабатывать каждую мышечную группу чаще, чем раз в неделю, что является мощным стимулом для роста. Программа сочетает дни с акцентом на силу (меньше повторений, больше вес) и на объем (больше повторений).",
                                                                                                        "muscleGroup": "Сплит",
                                                                                                        "goalType": "mass_gain",
                                                                                                        "program_exercise": [
                                                                                                          {
                                                                                                            "detail_id": "p2_d001",
                                                                                                            "exerciseId": 1,
                                                                                                            "order": 1,
                                                                                                            "dayOfWeek": "Понедельник",
                                                                                                            "sets": 4,
                                                                                                            "repetitions": 8,
                                                                                                            "durationSec": 40,
                                                                                                            "caloriesBurned": 60
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p2_d002",
                                                                                                            "exerciseId": 6,
                                                                                                            "order": 2,
                                                                                                            "dayOfWeek": "Понедельник",
                                                                                                            "sets": 4,
                                                                                                            "repetitions": 8,
                                                                                                            "durationSec": 40,
                                                                                                            "caloriesBurned": 55
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p2_d003",
                                                                                                            "exerciseId": 24,
                                                                                                            "order": 3,
                                                                                                            "dayOfWeek": "Понедельник",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 10,
                                                                                                            "durationSec": 45,
                                                                                                            "caloriesBurned": 45
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p2_d004",
                                                                                                            "exerciseId": 16,
                                                                                                            "order": 4,
                                                                                                            "dayOfWeek": "Понедельник",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 10,
                                                                                                            "durationSec": 45,
                                                                                                            "caloriesBurned": 40
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p2_d005",
                                                                                                            "exerciseId": 12,
                                                                                                            "order": 5,
                                                                                                            "dayOfWeek": "Понедельник",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 12,
                                                                                                            "durationSec": 45,
                                                                                                            "caloriesBurned": 30
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p2_d006",
                                                                                                            "exerciseId": 2,
                                                                                                            "order": 1,
                                                                                                            "dayOfWeek": "Среда",
                                                                                                            "sets": 4,
                                                                                                            "repetitions": 8,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 70
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p2_d007",
                                                                                                            "exerciseId": 14,
                                                                                                            "order": 2,
                                                                                                            "dayOfWeek": "Среда",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 10,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 50
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p2_d008",
                                                                                                            "exerciseId": 15,
                                                                                                            "order": 3,
                                                                                                            "dayOfWeek": "Среда",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 12,
                                                                                                            "durationSec": 60,
                                                                                                            "caloriesBurned": 45
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p2_d009",
                                                                                                            "exerciseId": 8,
                                                                                                            "order": 4,
                                                                                                            "dayOfWeek": "Среда",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 12,
                                                                                                            "durationSec": 45,
                                                                                                            "caloriesBurned": 30
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p2_d010",
                                                                                                            "exerciseId": 19,
                                                                                                            "order": 5,
                                                                                                            "dayOfWeek": "Среда",
                                                                                                            "sets": 4,
                                                                                                            "repetitions": 20,
                                                                                                            "durationSec": 45,
                                                                                                            "caloriesBurned": 35
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p2_d011",
                                                                                                            "exerciseId": 10,
                                                                                                            "order": 1,
                                                                                                            "dayOfWeek": "Пятница",
                                                                                                            "sets": 4,
                                                                                                            "repetitions": 12,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 55
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p2_d012",
                                                                                                            "exerciseId": 4,
                                                                                                            "order": 2,
                                                                                                            "dayOfWeek": "Пятница",
                                                                                                            "sets": 4,
                                                                                                            "repetitions": 10,
"durationSec": 60,
                                                                                                            "caloriesBurned": 50
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p2_d013",
                                                                                                            "exerciseId": 11,
                                                                                                            "order": 3,
                                                                                                            "dayOfWeek": "Пятница",
                                                                                                            "sets": 4,
                                                                                                            "repetitions": 15,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 35
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p2_d014",
                                                                                                            "exerciseId": 25,
                                                                                                            "order": 4,
                                                                                                            "dayOfWeek": "Пятница",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 15,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 30
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p2_d015",
                                                                                                            "exerciseId": 26,
                                                                                                            "order": 5,
                                                                                                            "dayOfWeek": "Пятница",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 12,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 25
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p2_d016",
                                                                                                            "exerciseId": 21,
                                                                                                            "order": 6,
                                                                                                            "dayOfWeek": "Пятница",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 20,
                                                                                                            "durationSec": 45,
                                                                                                            "caloriesBurned": 20
                                                                                                          }
                                                                                                        ]
                                                                                                      },
                                                                                                      {
                                                                                                        "program_id": "prog_mass_fb_003",
                                                                                                        "name": "Атлетизм и Масса: Фулбоди",
                                                                                                        "description": "4-недельная программа тренировок на все тело (Фулбоди) 3 раза в неделю. Каждая тренировка прорабатывает все основные мышечные группы, но имеет свой акцент для предотвращения монотонности и стимуляции роста. Отличный вариант для выработки анаболических гормонов и повышения общей функциональной силы.",
                                                                                                        "muscleGroup": "Фуллбоди",
                                                                                                        "goalType": "mass_gain",
                                                                                                        "program_exercise": [
                                                                                                          {
                                                                                                            "detail_id": "p3_d001",
                                                                                                            "exerciseId": 2,
                                                                                                            "order": 1,
                                                                                                            "dayOfWeek": "Понедельник",
                                                                                                            "sets": 4,
                                                                                                            "repetitions": 10,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 70
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p3_d002",
                                                                                                            "exerciseId": 1,
                                                                                                            "order": 2,
                                                                                                            "dayOfWeek": "Понедельник",
                                                                                                            "sets": 4,
                                                                                                            "repetitions": 10,
                                                                                                            "durationSec": 45,
                                                                                                            "caloriesBurned": 65
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p3_d003",
                                                                                                            "exerciseId": 16,
                                                                                                            "order": 3,
                                                                                                            "dayOfWeek": "Понедельник",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 12,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 40
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p3_d004",
                                                                                                            "exerciseId": 24,
                                                                                                            "order": 4,
                                                                                                            "dayOfWeek": "Понедельник",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 10,
                                                                                                            "durationSec": 45,
                                                                                                            "caloriesBurned": 45
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p3_d005",
                                                                                                            "exerciseId": 13,
                                                                                                            "order": 5,
                                                                                                            "dayOfWeek": "Понедельник",
                                                                                                            "sets": 2,
                                                                                                            "repetitions": 15,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 25
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p3_d006",
                                                                                                            "exerciseId": 20,
                                                                                                            "order": 6,
                                                                                                            "dayOfWeek": "Понедельник",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 1,
                                                                                                            "durationSec": 60,
                                                                                                            "caloriesBurned": 15
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p3_d007",
                                                                                                            "exerciseId": 3,
                                                                                                            "order": 1,
                                                                                                            "dayOfWeek": "Среда",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 8,
                                                                                                            "durationSec": 45,
                                                                                                            "caloriesBurned": 75
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p3_d008",
                                                                                                            "exerciseId": 4,
                                                                                                            "order": 2,
                                                                                                            "dayOfWeek": "Среда",
                                                                                                            "sets": 4,
                                                                                                            "repetitions": 10,
                                                                                                            "durationSec": 60,
                                                                                                            "caloriesBurned": 50
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p3_d009",
                                                                                                            "exerciseId": 18,
                                                                                                            "order": 3,
                                                                                                            "dayOfWeek": "Среда",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 15,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 55
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p3_d010",
                                                                                                            "exerciseId": 7,
                                                                                                            "order": 4,
"dayOfWeek": "Среда",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 12,
                                                                                                            "durationSec": 45,
                                                                                                            "caloriesBurned": 40
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p3_d011",
                                                                                                            "exerciseId": 11,
                                                                                                            "order": 5,
                                                                                                            "dayOfWeek": "Среда",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 15,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 30
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p3_d012",
                                                                                                            "exerciseId": 22,
                                                                                                            "order": 6,
                                                                                                            "dayOfWeek": "Среда",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 20,
                                                                                                            "durationSec": 60,
                                                                                                            "caloriesBurned": 25
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p3_d013",
                                                                                                            "exerciseId": 10,
                                                                                                            "order": 1,
                                                                                                            "dayOfWeek": "Пятница",
                                                                                                            "sets": 4,
                                                                                                            "repetitions": 12,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 55
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p3_d014",
                                                                                                            "exerciseId": 15,
                                                                                                            "order": 2,
                                                                                                            "dayOfWeek": "Пятница",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 15,
                                                                                                            "durationSec": 60,
                                                                                                            "caloriesBurned": 50
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p3_d015",
                                                                                                            "exerciseId": 17,
                                                                                                            "order": 3,
                                                                                                            "dayOfWeek": "Пятница",
                                                                                                            "sets": 4,
                                                                                                            "repetitions": 12,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 45
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p3_d016",
                                                                                                            "exerciseId": 12,
                                                                                                            "order": 4,
                                                                                                            "dayOfWeek": "Пятница",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 12,
                                                                                                            "durationSec": 45,
                                                                                                            "caloriesBurned": 30
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p3_d017",
                                                                                                            "exerciseId": 26,
                                                                                                            "order": 5,
                                                                                                            "dayOfWeek": "Пятница",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 15,
                                                                                                            "durationSec": 50,
                                                                                                            "caloriesBurned": 25
                                                                                                          },
                                                                                                          {
                                                                                                            "detail_id": "p3_d018",
                                                                                                            "exerciseId": 21,
                                                                                                            "order": 6,
                                                                                                            "dayOfWeek": "Пятница",
                                                                                                            "sets": 3,
                                                                                                            "repetitions": 20,
                                                                                                            "durationSec": 45,
                                                                                                            "caloriesBurned": 20
                                                                                                          }
                                                                                                        ]
                                                                                                      }
                                                                                                    ]
        """.trimIndent()
        val programDto: List<ProgramDto> = Json.decodeFromString(testResponse)
        return programDto.map { it.toDomain() }
    }

    override suspend fun generateMealPlan(
        userData: UsersData,
        goal: String,
        availableMeals: Map<Int?, String>,
        exceptionProducts: List<String>
    ): MealPlan {
//        val response = generator.generateMealPlan(userData, goal, availableMeals, exceptionProducts)
//            .trimIndent()
//            .removePrefix("```json")
//            .removeSuffix("```")
        val testResponse = """
            {
                                                                                                      "planName": "План питания для набора мышечной массы (~3100 ккал)",
                                                                                                      "description": "Сбалансированный рацион на 7 дней, нацеленный на гипертрофию мышц, с профицитом калорий. План содержит высокое количество белка и комплексных углеводов для эффективного роста и восстановления. Из рациона исключены продукты, содержащие молоко.",
                                                                                                      "goalType": "muscle_gain",
                                                                                                      "weeklyPlan": [
                                                                                                        {
                                                                                                          "dayOfWeek": "Понедельник",
                                                                                                          "meals": [
                                                                                                            {
                                                                                                              "mealId": 17,
                                                                                                              "mealType": "Завтрак",
                                                                                                              "quantity": 1,
                                                                                                              "notes": "Готовить без добавления молока."
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 6,
                                                                                                              "mealType": "Обед",
                                                                                                              "quantity": 1,
                                                                                                              "notes": ""
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 8,
                                                                                                              "mealType": "Перекус",
                                                                                                              "quantity": 1,
                                                                                                              "notes": "Приготовить на воде или растительном молоке (миндальное, соевое). Идеально после тренировки."
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 10,
                                                                                                              "mealType": "Ужин",
                                                                                                              "quantity": 1,
                                                                                                              "notes": ""
                                                                                                            }
                                                                                                          ]
                                                                                                        },
                                                                                                        {
                                                                                                          "dayOfWeek": "Вторник",
                                                                                                          "meals": [
                                                                                                            {
                                                                                                              "mealId": 19,
                                                                                                              "mealType": "Завтрак",
                                                                                                              "quantity": 1,
                                                                                                              "notes": ""
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 18,
                                                                                                              "mealType": "Обед",
                                                                                                              "quantity": 1,
                                                                                                              "notes": ""
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 22,
                                                                                                              "mealType": "Перекус",
                                                                                                              "quantity": 1,
                                                                                                              "notes": ""
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 2,
                                                                                                              "mealType": "Ужин",
                                                                                                              "quantity": 1,
                                                                                                              "notes": "Лосось богат омега-3 жирными кислотами, полезными для восстановления."
                                                                                                            }
                                                                                                          ]
                                                                                                        },
                                                                                                        {
                                                                                                          "dayOfWeek": "Среда",
                                                                                                          "meals": [
                                                                                                            {
                                                                                                              "mealId": 1,
                                                                                                              "mealType": "Завтрак",
                                                                                                              "quantity": 1,
                                                                                                              "notes": ""
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 13,
                                                                                                              "mealType": "Обед",
                                                                                                              "quantity": 1,
                                                                                                              "notes": ""
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 8,
                                                                                                              "mealType": "Перекус",
                                                                                                              "quantity": 1,
                                                                                                              "notes": "Приготовить на воде или растительном молоке. Употребить в течение часа после тренировки."
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 21,
                                                                                                              "mealType": "Ужин",
                                                                                                              "quantity": 1,
                                                                                                              "notes": ""
                                                                                                            }
                                                                                                          ]
                                                                                                        },
                                                                                                        {
                                                                                                          "dayOfWeek": "Четверг",
                                                                                                          "meals": [
                                                                                                            {
                                                                                                              "mealId": 17,
                                                                                                              "mealType": "Завтрак",
                                                                                                              "quantity": 1,
                                                                                                              "notes": "Готовить без добавления молока."
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 1,
                                                                                                              "mealType": "Обед",
                                                                                                              "quantity": 1,
                                                                                                              "notes": ""
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 22,
                                                                                                              "mealType": "Перекус",
                                                                                                              "quantity": 1,
                                                                                                              "notes": ""
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 20,
                                                                                                              "mealType": "Ужин",
                                                                                                              "quantity": 1,
                                                                                                              "notes": "Легкий ужин с высоким содержанием белка."
                                                                                                            }
                                                                                                          ]
                                                                                                        },
                                                                                                        {
                                                                                                          "dayOfWeek": "Пятница",
                                                                                                          "meals": [
                                                                                                            {
                                                                                                              "mealId": 19,
                                                                                                              "mealType": "Завтрак",
                                                                                                              "quantity": 1,
                                                                                                              "notes": ""
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 6,
                                                                                                              "mealType": "Обед",
                                                                                                              "quantity": 1,
                                                                                                              "notes": ""
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 8,
                                                                                                              "mealType": "Перекус",
                                                                                                              "quantity": 1,
                                                                                                              "notes": "Приготовить на воде или растительном молоке. Быстрое восстановление после тренировки."
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 15,
                                                                                                              "mealType": "Ужин",
                                                                                                              "quantity": 1,
                                                                                                              "notes": ""
                                                                                                            }
                                                                                                          ]
                                                                                                        },
                                                                                                        {
                                                                                                          "dayOfWeek": "Суббота",
                                                                                                          "meals": [
                                                                                                            {
                                                                                                              "mealId": 17,
                                                                                                              "mealType": "Завтрак",
                                                                                                              "quantity": 1,
                                                                                                              "notes": "Готовить без добавления молока. Можно добавить свежие овощи."
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 16,
                                                                                                              "mealType": "Обед",
                                                                                                              "quantity": 1,
                                                                                                              "notes": "Отличный источник растительного белка и полезных жиров."
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 5,
                                                                                                              "mealType": "Перекус",
                                                                                                              "quantity": 1,
                                                                                                              "notes": ""
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 10,
                                                                                                              "mealType": "Ужин",
                                                                                                              "quantity": 1,
                                                                                                              "notes": ""
                                                                                                            }
                                                                                                          ]
                                                                                                        },
                                                                                                        {
                                                                                                          "dayOfWeek": "Воскресенье",
                                                                                                          "meals": [
                                                                                                            {
                                                                                                              "mealId": 1,
                                                                                                              "mealType": "Завтрак",
                                                                                                              "quantity": 1,
                                                                                                              "notes": ""
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 18,
                                                                                                              "mealType": "Обед",
                                                                                                              "quantity": 1,
                                                                                                              "notes": ""
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 22,
                                                                                                              "mealType": "Перекус",
                                                                                                              "quantity": 1,
                                                                                                              "notes": ""
                                                                                                            },
                                                                                                            {
                                                                                                              "mealId": 2,
                                                                                                              "mealType": "Ужин",
                                                                                                              "quantity": 1,
                                                                                                              "notes": ""
                                                                                                            }
                                                                                                          ]
                                                                                                        }
                                                                                                      ]
                                                                                                    }
        """.trimIndent()
        Log.d("Json", testResponse)
        val mealDto: MealPlanDto = Json.decodeFromString(testResponse)
        return mealDto.toDomain(userData.id)
    }
}