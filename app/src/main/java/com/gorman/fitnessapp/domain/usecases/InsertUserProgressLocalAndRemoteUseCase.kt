package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.UserProgress
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class InsertUserProgressLocalAndRemoteUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(userProgress: UserProgress) {
        val remoteId = firebaseRepository.insertUserProgress(userProgress)
        remoteId?.let {
            databaseRepository.insertUserProgress(userProgress.copy(firebaseId = it))
        }
    }
}