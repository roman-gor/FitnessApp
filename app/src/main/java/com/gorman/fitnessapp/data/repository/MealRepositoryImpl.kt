package com.gorman.fitnessapp.data.repository

import android.util.Log
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.AiRepository
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import com.gorman.fitnessapp.domain.repository.MealRepository
import com.gorman.fitnessapp.domain.usecases.GetMealsUseCase
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val getMealsUseCase: GetMealsUseCase,
    private val aiRepository: AiRepository,
    private val firebaseRepository: FirebaseRepository,
    private val databaseRepository: DatabaseRepository
): MealRepository {
    override suspend fun generateAndSyncMeal(
        usersData: UsersData,
        goal: String,
        exceptionProducts: List<String>
    ): String {
        val availableMeals = getMealsUseCase().associate { meal ->
            val key = meal.firebaseId.toIntOrNull()
            key to meal.name
        }
        val generatedMealPlan = aiRepository.generateMealPlan(
            usersData,
            goal,
            availableMeals,
            exceptionProducts)
        val isPlanValid = generatedMealPlan.template.name.isNotBlank() && generatedMealPlan.items.isNotEmpty()
        if (isPlanValid) {
            val userId = "0"
            val oldMealPlans = firebaseRepository.findUserMealPlanTemplate(userId)
            if (oldMealPlans.isNotEmpty()) {
                oldMealPlans.keys.forEach { templateId ->
                    firebaseRepository.deleteMealPlan(templateId)
                }
                Log.d("SyncMealPlanUseCase", "Старые планы питания для пользователя $userId удалены.")
            }
            val firebaseId = firebaseRepository.insertMealPlan(
                mealPlanItem = generatedMealPlan.items,
                mealPlanTemplate = generatedMealPlan.template,
                userId = userId
            )
            firebaseId?.let { id->
                val syncedMealPlan = generatedMealPlan.copy(
                    template = generatedMealPlan.template.copy(firebaseId = id),
                    items = generatedMealPlan.items.map { it.copy(firebaseId = id) }
                )
                databaseRepository.insertMealsItems(syncedMealPlan)
            }
        }
        return generatedMealPlan.toString()
    }
}