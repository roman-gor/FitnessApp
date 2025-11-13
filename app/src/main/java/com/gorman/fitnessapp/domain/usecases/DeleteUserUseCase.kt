package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
){
    suspend operator fun invoke(user: UsersData) {
        firebaseRepository.deleteUser(user)
    }
}