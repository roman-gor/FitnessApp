package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.MealRepository
import javax.inject.Inject

class GenerateAndSyncMealPlans @Inject constructor(
    private val mealRepository: MealRepository
){
    /**
     * Запускает процесс генерации и полной синхронизации нового плана питания для пользователя.
     */
    suspend operator fun invoke(usersData: UsersData,
                                goal: String,
                                exceptionProducts: List<String>): String {
        return mealRepository.generateAndSyncMeal(usersData, goal, exceptionProducts)
    }
}