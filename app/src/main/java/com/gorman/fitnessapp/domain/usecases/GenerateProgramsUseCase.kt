package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.AiRepository
import javax.inject.Inject

class GenerateProgramsUseCase @Inject constructor(
    private val aiRepository: AiRepository
) {
    suspend operator fun invoke(usersData: UsersData,
                                availableExercises: Map<Int, String>): List<Program> {
        return aiRepository.generatePrograms(usersData, availableExercises)
    }
}