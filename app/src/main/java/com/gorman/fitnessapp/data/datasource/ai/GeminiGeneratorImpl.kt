package com.gorman.fitnessapp.data.datasource.ai

import android.util.Log
import com.gorman.fitnessapp.data.models.room.MealPlanTemplateEntity
import com.gorman.fitnessapp.data.models.room.UsersDataEntity
import javax.inject.Inject

class GeminiGeneratorImpl @Inject constructor(
    private val apiClient: AiApiClient
): GeminiGenerator {
    override suspend fun generateWorkoutProgram(
        userData: UsersDataEntity,
        num: Int,
        availableExercises: Map<Int, String>
    ): String {
        val exerciseList = availableExercises.entries.joinToString(separator = ", ") {
            "${it.key}: ${it.value}"
        }

        val prompt = """
            Ты — опытный фитнес-тренер, специализирующийся на составлении программ тренировок. 
            Твоя задача — сгенерировать $num уникальные, детализированные программы тренировок 
            на 4 недели для нового пользователя, основываясь на его данных. 
            
            --- ДАННЫЕ ПОЛЬЗОВАТЕЛЯ ---
            - Основная цель: ${userData.goal}
            - Уровень опыта: ${userData.experienceLevel}
            - Вес: ${userData.weight} кг
            - Желаемый вес: ${userData.desiredWeight} кг
            - Уровень активности: ${userData.activityLevel}
            - Тренировочные дни: 3 дня в неделю (например, ПН, СР, ПТ).
            
            --- ИНСТРУКЦИИ ---
            1. Каждая программа должна иметь уникальную фокусировку (например, "Базовая сила", "Гибрид", "Выносливость").
            2. Используй группы мышц из этого Map {1 - Грудь, 2 - Плечи, 3 - Трицепс, 4 - Ягодицы, 5 - Поясница, 6 - Верх спины, 7 - Бицепс бедра, 8 - Сгибатели бедра, 9 - Широчайшие мышцы спины, 10 - Бицепс, 11 - Предплечья, 12 - Кор, 13 - Квадрицепсы, 14 - Икроножные мышцы, 15 - Прямая мышца живота, 16 - Поперечная мышца живота, 17 - Косые мышцы живота.}
            3. Сгенерируй последовательность упражнений для каждого тренировочного дня (ПН, СР, ПТ).
            4. Используй числовые ID для упражнений (например 1, 2) СТРОГО из предоставленного списка.
            5. Поле goalType должно соответствовать цели пользователя (например, 'mass_gain').
            6. Разделяй упражнения по группам мышц: для каждого дня отдельная группа.
            7. Используй упражнения СТРОГО из этого списка: $exerciseList.
            8. Определяй durationSec каждого упражнения по средним затратам времени.
            9. Спасибо!
            
            --- ФОРМАТ ВЫВОДА (ВАЖНО!) ---
            Твой ответ должен быть **только** в формате JSON, соответствующем предоставленной схеме: $JSON_SCHEMA. 
            Не добавляй пояснений, приветствий или дополнительного текста.
        """

        val rawResponse = apiClient.completion(prompt)
        Log.d("rawResponse", rawResponse)
        return rawResponse
    }

    override suspend fun generateMealPlan(
        userData: UsersDataEntity,
        goal: String,
        availableMeals: Map<Int, String>
    ): String {
        val mealsList = availableMeals.entries.joinToString(separator = ", ") {
            "${it.key}: ${it.value}"
        }
        val prompt = """ """
        val rawResponse = apiClient.completion(prompt)
        Log.d("rawResponse", rawResponse)
        return rawResponse
    }

}