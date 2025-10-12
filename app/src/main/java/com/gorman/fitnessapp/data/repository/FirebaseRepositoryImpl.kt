package com.gorman.fitnessapp.data.repository

import com.gorman.fitnessapp.data.datasource.remote.FirebaseAPI
import com.gorman.fitnessapp.data.mapper.toDomain
import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAPI: FirebaseAPI
): FirebaseRepository {
    override suspend fun getExercises(): List<Exercise> {

        return firebaseAPI.getExercises().map {
            it.toDomain()
        }
    }
}