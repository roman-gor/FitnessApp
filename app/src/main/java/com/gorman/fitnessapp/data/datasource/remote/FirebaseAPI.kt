package com.gorman.fitnessapp.data.datasource.remote

import com.gorman.fitnessapp.data.models.ExerciseEntity

interface FirebaseAPI {
    fun getExercises(): List<ExerciseEntity>
}