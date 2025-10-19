package com.gorman.fitnessapp.data.datasource.ai

import com.gorman.fitnessapp.data.models.room.MealPlanTemplateEntity
import com.gorman.fitnessapp.data.models.room.UsersDataEntity
import com.gorman.fitnessapp.domain.models.UsersData

interface GeminiGenerator {
    suspend fun generateWorkoutProgram(userData: UsersData, num: Int = 3, availableExercises: Map<Int, String>): String
    suspend fun generateMealPlan(userData: UsersData,
                                 goal: String,
                                 availableMeals: Map<Int, String>,
                                 exceptionProducts: List<String>): String
}