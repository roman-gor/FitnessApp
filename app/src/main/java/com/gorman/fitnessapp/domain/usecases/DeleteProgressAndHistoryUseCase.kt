package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteProgressAndHistoryUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke() =
        databaseRepository.deleteUserProgressAndHistory()
}