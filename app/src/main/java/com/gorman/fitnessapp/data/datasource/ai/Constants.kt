package com.gorman.fitnessapp.data.datasource.ai

const val JSON_SCHEMA = """
            {
              "type": "object",
              "description": "Сгенерированная программа тренировок с метаданными и деталями упражнений.",
              "properties": {
                "program_id": {
                  "type": "string",
                  "description": "Уникальный строковый ID программы (например, prog_2024_005)."
                },
                "name": {
                  "type": "string",
                  "description": "Название программы (например, Масса, Неделя 1)."
                },
                "description": {
                  "type": "string",
                  "description": "Детальное описание цикла тренировок."
                },
                "muscleGroup": {
                  "type": "string",
                  "description": "Основная фокусировка (например, Грудь, Ноги или Фуллбоди)."
                },
                "goalType": {
                  "type": "string",
                  "description": "Тип цели (mass_gain, weight_loss, endurance)."
                },
                "program_exercise": {
                  "type": "array",
                  "description": "Массив упражнений, составляющих программу.",
                  "items": {
                    "type": "object",
                    "properties": {
                      "detail_id": {
                        "type": "string",
                        "description": "Уникальный ID детали упражнения в программе (например, detail_001)."
                      },
                      "exerciseId": {
                        "type": "integer",
                        "description": "Числовой ID упражнения, который соответствует справочнику 'exercises' (например, 1, 2, 3...)."
                      },
                      "order": {
                        "type": "integer",
                        "description": "Порядок выполнения упражнения в этот день."
                      },
                      "dayOfWeek": {
                        "type": "string",
                        "description": "День недели для выполнения (например, Понедельник)."
                      },
                      "sets": {
                        "type": "integer",
                        "description": "Количество подходов."
                      },
                      "repetitions": {
                        "type": "integer",
                        "description": "Количество повторений в подходе."
                      },
                      "durationSec": {
                        "type": "integer",
                        "description": "Продолжительность в секундах."
                      },
                      "caloriesBurned": {
                        "type": "integer",
                        "description": "Оценка сожженных калорий для этого упражнения."
                      }
                    },
                    "required": ["detail_id", "exerciseId", "order", "dayOfWeek", "sets", "repetitions"]
                  }
                }
              },
              "required": ["program_id", "name", "description", "goalType", "program_exercise"]
            }
        """
const val MEALS_JSON_SCHEMA = """
            {
              "type": "object",
              "description": "Сгенерированный персонализированный план питания на одну неделю.",
              "properties": {
                "planName": {
                  "type": "string",
                  "description": "Название плана питания (например, 'Сбалансированный рацион на 1800 ккал'). Соответствует полю 'name' в MealPlanTemplateEntity."
                },
                "description": {
                  "type": "string",
                  "description": "Краткое описание и цели плана питания. Соответствует полю 'description' в MealPlanTemplateEntity."
                },
                "goalType": {
                  "type": "string",
                  "description": "Тип цели, для которой создан план (например, 'weight_loss', 'muscle_gain', 'maintenance')."
                },
                "weeklyPlan": {
                  "type": "array",
                  "description": "Массив объектов, где каждый объект представляет собой план питания на один день недели.",
                  "items": {
                    "type": "object",
                    "properties": {
                      "dayOfWeek": {
                        "type": "string",
                        "description": "День недели (например, 'Понедельник', 'Вторник')."
                      },
                      "meals": {
                        "type": "array",
                        "description": "Массив приемов пищи на этот день. Каждый элемент соответствует одной записи MealPlanItemEntity.",
                        "items": {
                          "type": "object",
                          "properties": {
                            "mealId": {
                              "type": "integer",
                              "description": "Числовой ID блюда из предоставленного списка availableMeals. Соответствует 'mealId'."
                            },
                            "mealType": {
                              "type": "string",
                              "description": "Тип приема пищи (например, 'Завтрак', 'Обед', 'Ужин', 'Перекус'). Соответствует 'mealType'."
                            },
                            "quantity": {
                              "type": "integer",
                              "description": "Количество порций. Обычно 1. Соответствует 'quantity'."
                            },
                            "notes": {
                              "type": "string",
                              "description": "Необязательные заметки к приему пищи (например, 'Добавить свежие овощи'). Соответствует 'notes'."
                            }
                          },
                          "required": ["mealId", "mealType", "quantity"]
                        }
                      }
                    },
                    "required": ["dayOfWeek", "meals"]
                  }
                }
              },
              "required": ["planName", "description", "goalType", "weeklyPlan"]
            }
            """