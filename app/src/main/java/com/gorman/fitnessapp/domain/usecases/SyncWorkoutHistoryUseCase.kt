package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class SyncWorkoutHistoryUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(userId: String) {
        val remoteHistory = firebaseRepository.getWorkoutHistory(userId)
        databaseRepository.insertWorkoutHistory(remoteHistory)
    }
}