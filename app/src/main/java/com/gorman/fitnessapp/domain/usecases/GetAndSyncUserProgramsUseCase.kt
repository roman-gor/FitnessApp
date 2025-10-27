package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.SupabaseRepository
import javax.inject.Inject

class GetAndSyncUserProgramsUseCase @Inject constructor(
    private val supabaseRepository: SupabaseRepository,
    private val databaseRepository: DatabaseRepository
) {
    /**
     * Извлечение актуальной программы тренировок из облака и синхронизация её с локальной базой данных
     */
    suspend operator fun invoke(userId: Int){
        val programOutput = supabaseRepository.getProgram(userId)
        val programId = programOutput?.template?.let {
            databaseRepository.insertProgramWithExercises(it)
        }
        if (programOutput != null && programId != null){
            val userProgramToInsert = programOutput.userProgram.copy(programId = programId)
            databaseRepository.insertUserProgram(userProgramToInsert)
        }
    }
}