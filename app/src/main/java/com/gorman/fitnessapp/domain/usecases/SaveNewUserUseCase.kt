package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.SupabaseRepository
import javax.inject.Inject

class SaveNewUserUseCase @Inject constructor(
    private val supabaseRepository: SupabaseRepository,
    private val databaseRepository: DatabaseRepository,
    private val setUserIdUseCase: SetUserIdUseCase
) {
    suspend operator fun invoke(user: UsersData) {
        val supabaseId = supabaseRepository.insertUser(user)
        supabaseId?.let {
            databaseRepository.addUser(user.copy(supabaseId = it))
            setUserIdUseCase(it)
        }
    }
}