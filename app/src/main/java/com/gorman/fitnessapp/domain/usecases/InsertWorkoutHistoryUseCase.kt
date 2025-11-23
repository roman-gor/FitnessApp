package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.WorkoutHistory
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class InsertWorkoutHistoryUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(workoutHistory: WorkoutHistory): Result<Unit> {
        return try {
            val remoteId = firebaseRepository.insertWorkoutHistory(workoutHistory)
            remoteId?.let {
                databaseRepository.insertWorkoutHistory(workoutHistory.copy(firebaseId = it))
            }
            Result.success(Unit)
        } catch (e: IllegalStateException) {
            Result.failure(exception = e)
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }
}