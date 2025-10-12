package com.gorman.fitnessapp.data.datasource.remote

import com.gorman.fitnessapp.data.models.ExerciseEntity

interface FirebaseAPI {
    suspend fun getExercises(): List<ExerciseEntity>

}