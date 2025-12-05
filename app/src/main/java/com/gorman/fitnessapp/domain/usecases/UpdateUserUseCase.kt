package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.SupabaseRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val supabaseRepository: SupabaseRepository,
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(user: UsersData) {
        supabaseRepository.updateUser(user)
        databaseRepository.updateUser(user, user.id)
    }
}