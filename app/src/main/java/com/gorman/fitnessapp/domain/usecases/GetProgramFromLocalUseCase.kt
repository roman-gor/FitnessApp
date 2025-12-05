package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.ProgramExercise
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetProgramFromLocalUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(): Map<Program, List<ProgramExercise>> {
        val program = databaseRepository.getProgram()
        val programExercises = databaseRepository.getListOfProgramExercises(program.localId)
        return mapOf(
            program to programExercises
        )
    }
}