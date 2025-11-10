package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import com.gorman.fitnessapp.domain.repository.SettingsRepository
import com.gorman.fitnessapp.logger.AppLogger
import javax.inject.Inject

class SaveNewUserUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val databaseRepository: DatabaseRepository,
    private val settingsRepository: SettingsRepository,
    private val logger: AppLogger
) {
    suspend operator fun invoke(user: UsersData) {
        val firebaseId = firebaseRepository.insertUser(user)
        logger.d("USER", firebaseId.toString())
        firebaseId?.let {
            databaseRepository.addUser(user.copy(firebaseId = it))
            settingsRepository.setUserId(it)
            settingsRepository.setProgramId("")
        }
    }
}