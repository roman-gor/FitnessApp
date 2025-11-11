package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import com.gorman.fitnessapp.domain.repository.SettingsRepository
import javax.inject.Inject

class GetUserFromFirebaseUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val firebaseRepository: FirebaseRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(email: String): Boolean {
        val user = firebaseRepository.getUser(email)
        user?.let {
            databaseRepository.addUser(it)
            settingsRepository.setUserId(it.firebaseId)
            return true
        }
        return false
    }
}