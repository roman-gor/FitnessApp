package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.AiRepository
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetProgramsAndInsertIntoDBUseCase @Inject constructor(
    private val aiRepository: AiRepository,
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(usersData: UsersData, availableExercises: Map<Int, String>) {
        val programs = aiRepository.generatePrograms(usersData, availableExercises)
        programs.forEach { program ->
            databaseRepository.insertProgramWithExercises(program)
        }
    }
}