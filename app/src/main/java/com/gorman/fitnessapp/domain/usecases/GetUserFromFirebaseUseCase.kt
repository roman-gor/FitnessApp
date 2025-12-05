package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.SettingsRepository
import com.gorman.fitnessapp.domain.repository.SupabaseRepository
import javax.inject.Inject

class GetUserFromFirebaseUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val supabaseRepository: SupabaseRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(email: String): Boolean {
        val user = supabaseRepository.getUser(email)
        user?.let {
            databaseRepository.addUser(it)
            settingsRepository.setUserId(it.supabaseId)
            return true
        }
        return false
    }
}