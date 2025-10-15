package com.gorman.fitnessapp.domain.usecases

import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetAndSaveUserDataUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke(email: String): UsersData? {
        val user = firebaseRepository.getUser(email)
        user?.let {
            databaseRepository.addUser(it)
        }
        return user
    }
}