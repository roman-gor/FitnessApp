package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.UserProgress
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetUserProgressFromLocalUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
){
    suspend operator fun invoke(): List<UserProgress> {
        return databaseRepository.getUserProgress()
    }
}