package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.SupabaseRepository
import javax.inject.Inject

class UpdateExercisesUseCase @Inject constructor(
    private val supabaseRepository: SupabaseRepository,
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke() {
        val exercises = supabaseRepository.getExercises()
        databaseRepository.updateExercises(exercises)
    }
}