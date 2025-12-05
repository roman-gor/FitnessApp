package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.SupabaseRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val supabaseRepository: SupabaseRepository
){
    suspend operator fun invoke(user: UsersData) {
        supabaseRepository.deleteUser(user)
    }
}