package com.gorman.fitnessapp.domain.repository

import com.gorman.fitnessapp.domain.models.MealPlan
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.UsersData

interface AiRepository {
    suspend fun generatePrograms(
        usersData: UsersData,
        availableExercises: Map<Int?, String>
    ): List<Program>
    suspend fun generateMealPlan(
        userData: UsersData,
        goal: String,
        availableMeals: Map<Int?, String>,
        exceptionProducts: List<String>
    ): MealPlan
}