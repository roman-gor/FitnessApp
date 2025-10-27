package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.SupabaseRepository
import com.gorman.fitnessapp.domain.repository.SettingsRepository
import javax.inject.Inject

class SaveNewUserUseCase @Inject constructor(
    private val supabaseRepository: SupabaseRepository,
    private val databaseRepository: DatabaseRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(user: UsersData) {
        val firebaseId = supabaseRepository.insertUser(user)
        firebaseId?.let {
            databaseRepository.addUser(user.copy(supabaseId = it))
            settingsRepository.setUserId(it)
        }
    }
}