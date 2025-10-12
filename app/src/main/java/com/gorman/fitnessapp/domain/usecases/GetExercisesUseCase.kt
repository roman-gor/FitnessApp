package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetExercisesUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(): List<Exercise> {
        val localExercises = databaseRepository.getExercises()
        return localExercises.ifEmpty {
            val remoteExercises = firebaseRepository.getExercises()
            databaseRepository.insertExercises(remoteExercises)
            remoteExercises
        }
    }
}