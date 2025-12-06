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
        val response = availableExercises?.let {
            generator.generateWorkoutProgram(userData = usersData, availableExercises = it)
                .trimIndent()
                .removePrefix("```json")
                .removeSuffix("```")
        }
        Log.d("Json", "$response")
        val testResponse = """
            [
              {
                "program_id": "prog_mass_gain_001",
                "name": "Базовый набор массы, 4 недели",
                "description": "4-недельная программа для набора мышечной массы, основанная на 3-х разовых full-body тренировках в неделю. Основной акцент на базовых многосуставных упражнениях для стимуляции роста всех основных мышечных групп. Для достижения прогресса старайтесь каждую неделю немного увеличивать рабочий вес или количество повторений в указанном диапазоне (8-12), сохраняя правильную технику.",
                "muscleGroup": "Fullbody",
                "goalType": "mass_gain",
                "program_exercise": [
                  {
                    "detail_id": "detail_001",
                    "exerciseId": 3,
                    "order": 1,
                    "dayOfWeek": "Понедельник",
                    "sets": 4,
                    "repetitions": 10,
                    "durationSec": 120,
                    "caloriesBurned": 72
                  },
                  {
                    "detail_id": "detail_002",
                    "exerciseId": 20,
                    "order": 2,
                    "dayOfWeek": "Понедельник",
                    "sets": 3,
                    "repetitions": 12,
                    "durationSec": 120,
                    "caloriesBurned": 55
                  },
                  {
                    "detail_id": "detail_003",
                    "exerciseId": 7,
                    "order": 3,
                    "dayOfWeek": "Понедельник",
                    "sets": 4,
                    "repetitions": 10,
                    "durationSec": 120,
                    "caloriesBurned": 65
                  },
                  {
                    "detail_id": "detail_004",
                    "exerciseId": 9,
                    "order": 4,
                    "dayOfWeek": "Понедельник",
                    "sets": 3,
                    "repetitions": 12,
                    "durationSec": 120,
                    "caloriesBurned": 40
                  },
                  {
                    "detail_id": "detail_005",
                    "exerciseId": 12,
                    "order": 5,
                    "dayOfWeek": "Понедельник",
                    "sets": 3,
                    "repetitions": 12,
                    "durationSec": 120,
                    "caloriesBurned": 30
                  },
                  {
                    "detail_id": "detail_006",
                    "exerciseId": 15,
                    "order": 6,
                    "dayOfWeek": "Понедельник",
                    "sets": 3,
                    "repetitions": 12,
                    "durationSec": 120,
                    "caloriesBurned": 35
                  },
                  {
                    "detail_id": "detail_007",
                    "exerciseId": 25,
                    "order": 7,
                    "dayOfWeek": "Понедельник",
                    "sets": 3,
                    "repetitions": 15,
                    "durationSec": 120,
                    "caloriesBurned": 25
                  },
                  {
                    "detail_id": "detail_008",
                    "exerciseId": 6,
                    "order": 1,
                    "dayOfWeek": "Среда",
                    "sets": 4,
                    "repetitions": 10,
                    "durationSec": 120,
                    "caloriesBurned": 80
                  },
                  {
                    "detail_id": "detail_009",
                    "exerciseId": 1,
                    "order": 2,
                    "dayOfWeek": "Среда",
                    "sets": 3,
                    "repetitions": 12,
                    "durationSec": 120,
                    "caloriesBurned": 40
                  },
                  {
                    "detail_id": "detail_010",
                    "exerciseId": 22,
                    "order": 3,
                    "dayOfWeek": "Среда",
                    "sets": 3,
                    "repetitions": 12,
                    "durationSec": 120,
                    "caloriesBurned": 45
                  },
                  {
                    "detail_id": "detail_011",
                    "exerciseId": 10,
                    "order": 4,
                    "dayOfWeek": "Среда",
                    "sets": 3,
                    "repetitions": 15,
                    "durationSec": 120,
                    "caloriesBurned": 30
                  },
                  {
                    "detail_id": "detail_012",
                    "exerciseId": 13,
                    "order": 5,
                    "dayOfWeek": "Среда",
                    "sets": 3,
                    "repetitions": 12,
                    "durationSec": 120,
                    "caloriesBurned": 30
                  },
                  {
                    "detail_id": "detail_013",
                    "exerciseId": 14,
                    "order": 6,
                    "dayOfWeek": "Среда",
                    "sets": 3,
                    "repetitions": 12,
                    "durationSec": 120,
                    "caloriesBurned": 28
                  },
                  {
                    "detail_id": "detail_014",
                    "exerciseId": 17,
                    "order": 7,
                    "dayOfWeek": "Среда",
                    "sets": 3,
                    "repetitions": 1,
                    "durationSec": 45,
                    "caloriesBurned": 25
                  },
                  {
                    "detail_id": "detail_015",
                    "exerciseId": 28,
                    "order": 1,
                    "dayOfWeek": "Пятница",
                    "sets": 3,
                    "repetitions": 10,
                    "durationSec": 120,
                    "caloriesBurned": 60
                  },
                  {
                    "detail_id": "detail_016",
                    "exerciseId": 27,
                    "order": 2,
                    "dayOfWeek": "Пятница",
                    "sets": 3,
                    "repetitions": 12,
                    "durationSec": 120,
                    "caloriesBurned": 40
                  },
                  {
                    "detail_id": "detail_017",
                    "exerciseId": 7,
                    "order": 3,
                    "dayOfWeek": "Пятница",
                    "sets": 4,
                    "repetitions": 12,
                    "durationSec": 120,
                    "caloriesBurned": 70
                  },
                  {
                    "detail_id": "detail_018",
                    "exerciseId": 11,
                    "order": 4,
                    "dayOfWeek": "Пятница",
                    "sets": 3,
                    "repetitions": 12,
                    "durationSec": 120,
                    "caloriesBurned": 35
                  },
                  {
                    "detail_id": "detail_019",
                    "exerciseId": 29,
                    "order": 5,
                    "dayOfWeek": "Пятница",
                    "sets": 3,
                    "repetitions": 12,
                    "durationSec": 120,
                    "caloriesBurned": 25
                  },
                  {
                    "detail_id": "detail_020",
                    "exerciseId": 24,
                    "order": 6,
                    "dayOfWeek": "Пятница",
                    "sets": 3,
                    "repetitions": 20,
                    "durationSec": 120,
                    "caloriesBurned": 20
                  },
                  {
                    "detail_id": "detail_021",
                    "exerciseId": 16,
                    "order": 7,
                    "dayOfWeek": "Пятница",
                    "sets": 3,
                    "repetitions": 15,
                    "durationSec": 120,
                    "caloriesBurned": 28
                  }
                ]
              }
            ]
        """.trimIndent()
        val programDto: List<ProgramDto> = Json.decodeFromString(response!!)
        return programDto.map { it.toDomain() }
    }

    override suspend fun generateMealPlan(
        userData: UsersData,
        dietaryPreferences: String,
        calories: String,
        availableMeals: Map<Int?, String>,
        exceptionProducts: List<String>
    ): MealPlan {
        val response = generator.generateMealPlan(userData, dietaryPreferences, calories, availableMeals, exceptionProducts)
            .trimIndent()
            .removePrefix("```json")
            .removeSuffix("```")
        val testResponse = """
            {
                                                                                                      "planName": "План питания для набора мышечной массы",
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
        val mealDto: MealPlanDto = Json.decodeFromString(response)
        return mealDto.toDomain(userData.id)
    }
}