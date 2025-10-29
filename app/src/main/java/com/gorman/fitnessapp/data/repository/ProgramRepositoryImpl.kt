package com.gorman.fitnessapp.data.repository

import android.util.Log
import androidx.room.Transaction
import com.gorman.fitnessapp.domain.models.UserProgram
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.AiRepository
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.SupabaseRepository
import com.gorman.fitnessapp.domain.repository.ProgramRepository
import com.gorman.fitnessapp.domain.usecases.GetExercisesUseCase
import com.gorman.fitnessapp.domain.usecases.SetProgramIdUseCase
import java.time.Instant
import javax.inject.Inject

class ProgramRepositoryImpl @Inject constructor(
    private val supabaseRepository: SupabaseRepository,
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
        val availableExercises = getExercisesUseCase()?.associate { exercise ->
            val key: Int? = exercise.supabaseId
            key to exercise.name
        }
        val oldUserPrograms = supabaseRepository.findUserPrograms(usersData.supabaseId)
        Log.d("OldUserPrograms", oldUserPrograms.toString())
        if (oldUserPrograms.isNotEmpty()) {
            supabaseRepository.deleteAllUserPrograms(oldUserPrograms)
        }
        val generatedPrograms = aiRepository.generatePrograms(usersData, availableExercises)
        if (generatedPrograms.isNotEmpty()) {
            var selectedProgram = generatedPrograms[selectedProgramIndex]
            val supabaseProgramId = supabaseRepository.insertProgram(selectedProgram)
            supabaseProgramId?.let {
                selectedProgram = selectedProgram.copy(supabaseId = it)
            }

            val programId = databaseRepository.insertProgramWithExercises(selectedProgram)

            supabaseRepository.insertProgramExercise(
                programId = selectedProgram.supabaseId,
                programExercise = selectedProgram.exercises ?: emptyList()
            )
            val actualTimestamp: Long = Instant.now().toEpochMilli()
            if (supabaseProgramId != null) {
                val userProgram = UserProgram(
                    userId = usersData.supabaseId,
                    supabaseId = supabaseProgramId,
                    programId = programId,
                    startDate = actualTimestamp,
                    isCompleted = false
                )
                val userSupabaseProgram = UserProgram(
                    userId = usersData.supabaseId,
                    supabaseId = supabaseProgramId,
                    programId = supabaseProgramId,
                    startDate = actualTimestamp,
                    isCompleted = false
                )
                databaseRepository.insertUserProgram(userProgram)
                supabaseRepository.insertUserProgram(userSupabaseProgram)
                setProgramIdUseCase(supabaseProgramId)
            }
            Log.d("ProgramExercise", selectedProgram.supabaseId.toString())
        }
        return generatedPrograms.toString()
    }
}