package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.Meal
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetMealsUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(): List<Meal> {
        val localMeals = databaseRepository.getMeals()
        return localMeals.ifEmpty {
            val remoteMeals = firebaseRepository.getMeals()
            databaseRepository.insertMeals(remoteMeals)
            remoteMeals
        }
    }
}