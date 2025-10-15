package com.gorman.fitnessapp.data.datasource.ai

import com.gorman.fitnessapp.data.models.room.MealPlanTemplateEntity
import com.gorman.fitnessapp.data.models.room.UsersDataEntity

interface GeminiGenerator {
    suspend fun generateWorkoutProgram(userData: UsersDataEntity, num: Int = 3, availableExercises: Map<Int, String>): String
    suspend fun generateMealPlan(userData: UsersDataEntity, goal: String): MealPlanTemplateEntity
}