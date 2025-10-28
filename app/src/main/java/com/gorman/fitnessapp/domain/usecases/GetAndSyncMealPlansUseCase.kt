package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.SupabaseRepository
import javax.inject.Inject

class GetAndSyncMealPlansUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val supabaseRepository: SupabaseRepository
) {
    suspend operator fun invoke(userId: Int) {
        val remoteMealPlans = supabaseRepository.getMealPlans(userId)
        remoteMealPlans?.let { databaseRepository.insertMealsItems(it) }
    }
}