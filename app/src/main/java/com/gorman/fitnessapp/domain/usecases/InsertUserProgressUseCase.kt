package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.UserProgress
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class InsertUserProgressUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(userProgress: UserProgress): Result<Unit> {
        return try {
            val remoteId = firebaseRepository.insertUserProgress(userProgress)
            remoteId?.let {
                databaseRepository.insertUserProgress(userProgress.copy(firebaseId = it))
            }
            Result.success(Unit)
        } catch (e: IllegalStateException) {
            Result.failure(exception = e)
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }
}