package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.WorkoutHistory
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetWorkoutHistoryFromLocalUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(): List<WorkoutHistory> {
        return databaseRepository.getWorkoutHistory()
    }
}