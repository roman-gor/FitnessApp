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
                        "type": "string",
                        "description": "Строковый ID упражнения, который соответствует справочнику 'exercises' (например, ex_001)."
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
                        "description": "Продолжительность (для кардио или планки) в секундах."
                      },
                      "caloriesBurned": {
                        "type": "number",
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