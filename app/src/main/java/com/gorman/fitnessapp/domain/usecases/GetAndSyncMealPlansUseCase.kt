package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.MealPlan
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetAndSyncMealPlansUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val databaseRepository: DatabaseRepository
) {
    /**
     * Извлечение актуальной программы питания из облака и синхронизация её с локальной базой данных
     */
    suspend operator fun invoke(userId: String) {
        val mealPlansOutput = firebaseRepository.getMealPlans(userId)
        mealPlansOutput?.firstNotNullOfOrNull { (template, items) ->
            val newMealPlan = MealPlan(template = template, items = items)
            databaseRepository.insertMealsItems(newMealPlan)
        }
    }
}