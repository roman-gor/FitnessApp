package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.MealPlanItem
import com.gorman.fitnessapp.domain.models.MealPlanTemplate
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetMealPlanFromLocalUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(): Pair<MealPlanTemplate, List<MealPlanItem>> {
        return databaseRepository.getMealPlan()
    }
}