package com.gorman.fitnessapp.data.repository

import android.util.Log
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.AiRepository
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.SupabaseRepository
import com.gorman.fitnessapp.domain.repository.MealRepository
import com.gorman.fitnessapp.domain.usecases.GetMealsUseCase
import com.gorman.fitnessapp.domain.usecases.SetMealsIdUseCase
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val getMealsUseCase: GetMealsUseCase,
    private val aiRepository: AiRepository,
    private val supabaseRepository: SupabaseRepository,
    private val databaseRepository: DatabaseRepository,
    private val setMealsIdUseCase: SetMealsIdUseCase
): MealRepository {
    override suspend fun generateAndSyncMeal(
        usersData: UsersData,
        dietaryPreferences: String,
        calories: String,
        exceptionProducts: List<String>
    ): String {
        val availableMeals = getMealsUseCase().associate { meal ->
            val key: Int? = meal.supabaseId
            key to meal.name
        }
        val generatedMealPlan = aiRepository.generateMealPlan(
            usersData,
            dietaryPreferences,
            calories,
            availableMeals,
            exceptionProducts)
        val isPlanValid = generatedMealPlan.template.name.isNotBlank() && generatedMealPlan.items.isNotEmpty()
        if (isPlanValid) {
            val userId = usersData.supabaseId
            val oldMealPlans = supabaseRepository.findUserMealPlanTemplate(userId)
            if (oldMealPlans.isNotEmpty()) {
                oldMealPlans.keys.forEach { templateId ->
                    supabaseRepository.deleteMealPlan(templateId)
                }
                Log.d("SyncMealPlanUseCase", "Старые планы питания для пользователя $userId удалены.")
            }
            val supabaseId = supabaseRepository.insertMealPlan(
                mealPlanItem = generatedMealPlan.items,
                mealPlanTemplate = generatedMealPlan.template,
                userId = userId
            )
            supabaseId?.let { id->
                val syncedMealPlan = generatedMealPlan.copy(
                    template = generatedMealPlan.template.copy(supabaseId = id),
                    items = generatedMealPlan.items.map { it.copy(supabaseId = id) }
                )
                databaseRepository.insertMealsItems(syncedMealPlan)
                setMealsIdUseCase(id)
                Log.d("MealId", id.toString())
            }
        }
        return generatedMealPlan.toString()
    }
}