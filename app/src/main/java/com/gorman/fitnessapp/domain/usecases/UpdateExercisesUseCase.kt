package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class UpdateExercisesUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke() {
        val exercises = firebaseRepository.getExercises()
        databaseRepository.updateExercises(exercises)
    }
}