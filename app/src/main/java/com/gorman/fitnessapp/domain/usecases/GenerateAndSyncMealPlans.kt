package com.gorman.fitnessapp.domain.usecases

import android.util.Log
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.AiRepository
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class GenerateAndSyncMealPlans @Inject constructor(
    private val aiRepository: AiRepository,
    private val databaseRepository: DatabaseRepository,
    private val firebaseRepository: FirebaseRepository
){
    suspend operator fun invoke(usersData: UsersData,
                                goal: String,
                                exceptionProducts: List<String>) {
        val oldMealPlans = firebaseRepository.findUserMealPlanTemplate("0")
        if (oldMealPlans.isNotEmpty()) {
            oldMealPlans.keys.forEach { templateId ->
                firebaseRepository.deleteMealPlan(templateId)
            }
            Log.d("SyncMealPlanUseCase", "Старые планы питания для пользователя ${"0"} удалены.")
        }

        val availableMeals = firebaseRepository.getMeals().associate { meal ->
            val key = meal.firebaseId.toIntOrNull()
            key to meal.name
        }
        val generatedMealPlan = aiRepository.generateMealPlan(
            usersData,
            goal,
            availableMeals,
            exceptionProducts)
        val firebaseId = firebaseRepository.insertMealPlan(
            mealPlanItem = generatedMealPlan.items,
            mealPlanTemplate = generatedMealPlan.template
        )
        firebaseId?.let { id->
            val syncedMealPlan = generatedMealPlan.copy(
                template = generatedMealPlan.template.copy(firebaseId = id)
            )
            databaseRepository.insertMealsItems(syncedMealPlan)
        }
    }
}