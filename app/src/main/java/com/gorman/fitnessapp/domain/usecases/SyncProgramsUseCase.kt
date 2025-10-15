package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.AiRepository
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import javax.inject.Inject

class SyncProgramsUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(programs: List<Program>, selectedProgramIndex: Int) {
        databaseRepository.insertProgramWithExercises(programs, selectedProgramIndex)
    }
}