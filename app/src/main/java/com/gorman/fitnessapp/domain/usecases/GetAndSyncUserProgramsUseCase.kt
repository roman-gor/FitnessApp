package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import com.gorman.fitnessapp.domain.repository.SettingsRepository
import javax.inject.Inject

class GetAndSyncUserProgramsUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val databaseRepository: DatabaseRepository,
    private val settingsRepository: SettingsRepository
) {
    /**
     * Извлечение актуальной программы тренировок из облака и синхронизация её с локальной базой данных
     */
    suspend operator fun invoke(userId: String) {
        val programOutput = firebaseRepository.getProgram(userId)
        val programId = programOutput?.template?.let {
            databaseRepository.insertProgramWithExercises(it)
        }
        if (programOutput != null && programId != null){
            val userProgramToInsert = programOutput.userProgram.copy(programId = programId)
            databaseRepository.insertUserProgram(userProgramToInsert)
            settingsRepository.setProgramId(programOutput.userProgram.firebaseId)
        }
    }
}