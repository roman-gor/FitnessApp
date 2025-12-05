package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.SupabaseRepository
import javax.inject.Inject

class SyncWorkoutHistoryUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val supabaseRepository: SupabaseRepository
) {
    suspend operator fun invoke(userId: Int) {
        val remoteHistory = supabaseRepository.getWorkoutHistory(userId)
        databaseRepository.insertWorkoutHistory(remoteHistory)
    }
}