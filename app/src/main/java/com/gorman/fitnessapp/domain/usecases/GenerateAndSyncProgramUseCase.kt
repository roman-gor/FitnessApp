package com.gorman.fitnessapp.domain.usecases

import android.util.Log
import com.gorman.fitnessapp.domain.models.UserProgram
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.AiRepository
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class GenerateAndSyncProgramUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val firebaseRepository: FirebaseRepository,
    private val aiRepository: AiRepository,
    private val getExercisesUseCase: GetExercisesUseCase
) {
    suspend operator fun invoke(usersData: UsersData,
                                selectedProgramIndex: Int): String {
        val availableExercises = getExercisesUseCase()?.associate { exercise ->
            val key = exercise.firebaseId.toIntOrNull()
            key to exercise.name
        }
        val generatedPrograms = aiRepository.generatePrograms(usersData, availableExercises)

        val selectedProgram = generatedPrograms[selectedProgramIndex]
        val firebaseId = firebaseRepository.insertProgram(selectedProgram)
        firebaseId?.let {
            selectedProgram.copy(firebaseId = it)
        }

        databaseRepository.insertProgramWithExercises(selectedProgram, selectedProgramIndex)

        firebaseRepository.insertProgramExercise(
            programId = selectedProgram.firebaseId,
            programExercise = selectedProgram.exercises ?: emptyList()
        )
        val userProgram = UserProgram(
            userId = "0",
            firebaseId = firebaseId,
            programId = 0,


        )
        Log.d("ProgramExercise", selectedProgram.firebaseId)
        return generatedPrograms.toString()
    }
}