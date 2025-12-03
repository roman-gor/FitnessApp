package com.gorman.fitnessapp.data.datasource.ai

import com.gorman.fitnessapp.domain.models.UsersData

interface GeminiGenerator {
    suspend fun generateWorkoutProgram(userData: UsersData,
                                       num: Int = 1,
                                       availableExercises: Map<Int?, String>): String
    suspend fun generateMealPlan(userData: UsersData,
                                 dietaryPreferences: String,
                                 calories: String,
                                 availableMeals: Map<Int?, String>,
                                 exceptionProducts: List<String>): String
}