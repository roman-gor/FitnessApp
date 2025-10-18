package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.AiRepository
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class GenerateAndSyncProgramUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val firebaseRepository: FirebaseRepository,
    private val aiRepository: AiRepository
) {
    suspend operator fun invoke(usersData: UsersData,
                                availableExercises: Map<Int, String>,
                                selectedProgramIndex: Int) {
        val generatedPrograms = aiRepository.generatePrograms(usersData, availableExercises)

        val syncedPrograms = generatedPrograms.mapNotNull { program ->
            val firebaseId = firebaseRepository.insertProgram(program)
            firebaseId?.let {
                program.copy(firebaseId = it)
            }
        }
        databaseRepository.insertProgramWithExercises(syncedPrograms, selectedProgramIndex)

        val selectedProgram = syncedPrograms[selectedProgramIndex]
        firebaseRepository.insertProgramExercise(
            programId = selectedProgram.firebaseId,
            programExercise = selectedProgram.exercises ?: emptyList()
        )
    }
}