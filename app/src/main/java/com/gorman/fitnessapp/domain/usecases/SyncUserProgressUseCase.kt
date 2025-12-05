package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.SupabaseRepository
import javax.inject.Inject

class SyncUserProgressUseCase @Inject constructor(
    private val supabaseRepository: SupabaseRepository,
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(userId: Int) {
        val remoteProgress = supabaseRepository.getUserProgress(userId)
        remoteProgress.let { databaseRepository.insertUserProgress(it) }
    }
}