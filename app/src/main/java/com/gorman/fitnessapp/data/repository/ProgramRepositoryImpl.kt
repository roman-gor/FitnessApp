package com.gorman.fitnessapp.data.repository

import android.util.Log
import androidx.room.Transaction
import com.gorman.fitnessapp.domain.models.UserProgram
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.AiRepository
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import com.gorman.fitnessapp.domain.repository.ProgramRepository
import com.gorman.fitnessapp.domain.usecases.GetExercisesUseCase
import com.gorman.fitnessapp.domain.usecases.SetProgramIdUseCase
import java.time.Instant
import javax.inject.Inject

class ProgramRepositoryImpl @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val aiRepository: AiRepository,
    private val databaseRepository: DatabaseRepository,
    private val getExercisesUseCase: GetExercisesUseCase,
    private val setProgramIdUseCase: SetProgramIdUseCase
): ProgramRepository {
    @Transaction
    override suspend fun generateAndSyncProgram(
        usersData: UsersData,
        selectedProgramIndex: Int
    ): String {
        Log.d("Exercises", "${getExercisesUseCase()}")
        val availableExercises = getExercisesUseCase().associate { exercise ->
            val key = exercise.firebaseId.toIntOrNull()
            key to exercise.name
        }
        val oldUserPrograms = firebaseRepository.findUserPrograms(usersData.firebaseId)
        if (oldUserPrograms.isNotEmpty()) {
            firebaseRepository.deleteAllUserPrograms(oldUserPrograms)
        }
        val generatedPrograms = aiRepository.generatePrograms(usersData, availableExercises)
        if (generatedPrograms.isNotEmpty()) {
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
                    userId = usersData.firebaseId,
                    firebaseId = firebaseProgramId,
                    programId = programId,
                    startDate = actualTimestamp,
                    isCompleted = false
                )
                databaseRepository.insertUserProgram(userProgram)
                firebaseRepository.insertUserProgram(userProgram)
                setProgramIdUseCase(firebaseProgramId)
            }
            Log.d("ProgramExercise", selectedProgram.firebaseId)
        }
        return generatedPrograms.toString()
    }
}