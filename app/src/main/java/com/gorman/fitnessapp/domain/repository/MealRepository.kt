package com.gorman.fitnessapp.domain.repository

import com.gorman.fitnessapp.domain.models.UsersData

interface MealRepository {
    suspend fun generateAndSyncMeal(usersData: UsersData,
                                    dietaryPreferences: String,
                                    calories: String,
                                    exceptionProducts: List<String>): String
}