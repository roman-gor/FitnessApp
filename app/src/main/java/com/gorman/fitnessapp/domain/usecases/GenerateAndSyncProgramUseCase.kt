package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.ProgramRepository
import javax.inject.Inject

class GenerateAndSyncProgramUseCase @Inject constructor(
    private val programRepository: ProgramRepository
) {
    /**
     * Запускает процесс генерации и полной синхронизации новой программы тренировок для пользователя.
     */
    suspend operator fun invoke(usersData: UsersData,
                                selectedProgramIndex: Int = 0): String {
        return programRepository.generateAndSyncProgram(usersData, selectedProgramIndex)
    }
}