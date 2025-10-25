package com.gorman.fitnessapp.domain.usecases

import android.util.Log
import com.gorman.fitnessapp.domain.models.UserProgram
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.AiRepository
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import java.time.Instant
import javax.inject.Inject

class GenerateAndSyncProgramUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val firebaseRepository: FirebaseRepository,
    private val aiRepository: AiRepository,
    private val getExercisesUseCase: GetExercisesUseCase
) {
    suspend operator fun invoke(usersData: UsersData,
                                selectedProgramIndex: Int): String {
        val oldUserPrograms = firebaseRepository.findUserPrograms("0")
        if (oldUserPrograms.isNotEmpty()) {
            firebaseRepository.deleteAllUserPrograms(oldUserPrograms)
        }

        val availableExercises = getExercisesUseCase()?.associate { exercise ->
            val key = exercise.firebaseId.toIntOrNull()
            key to exercise.name
        }
        val generatedPrograms = aiRepository.generatePrograms(usersData, availableExercises)

        var selectedProgram = generatedPrograms[selectedProgramIndex]
        val firebaseProgramId = firebaseRepository.insertProgram(selectedProgram)
        firebaseProgramId?.let {
            selectedProgram = selectedProgram.copy(firebaseId = it)
        }

        val programId = databaseRepository.insertProgramWithExercises(selectedProgram)

        firebaseRepository.insertProgramExercise(
            programId = selectedProgram.firebaseId,
            programExercise = selectedProgram.exercises ?: emptyList()
        )
        val actualTimestamp: Long = Instant.now().toEpochMilli()
        if (firebaseProgramId?.isNotEmpty() == true) {
            val userProgram = UserProgram(
                userId = "0",
                firebaseId = firebaseProgramId,
                programId = programId,
                startDate = actualTimestamp,
                isCompleted = false
            )
            databaseRepository.insertUserProgram(userProgram)
            firebaseRepository.insertUserProgram(userProgram)
        }
        Log.d("ProgramExercise", selectedProgram.firebaseId)
        return generatedPrograms.toString()
    }
}