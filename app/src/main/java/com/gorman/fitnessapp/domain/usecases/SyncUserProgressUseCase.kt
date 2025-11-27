package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class SyncUserProgressUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(userId: String) {
        val remoteProgress = firebaseRepository.getUserProgress(userId)
        remoteProgress?.let { databaseRepository.insertUserProgress(it) }
    }
}