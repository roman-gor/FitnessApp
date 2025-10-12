package com.gorman.fitnessapp.domain.repository

import com.gorman.fitnessapp.domain.models.Exercise

interface FirebaseRepository {
    suspend fun getExercises(): List<Exercise>
}