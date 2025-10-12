package com.gorman.fitnessapp.data.datasource.remote

import com.google.firebase.database.DatabaseReference
import com.gorman.fitnessapp.data.models.ExerciseEntity
import javax.inject.Inject

class FirebaseAPIImpl @Inject constructor(
    private val database: DatabaseReference
) : FirebaseAPI {
    override fun getExercises(): List<ExerciseEntity> {
        val exerciseRef = database.child("exercises")
        //TODO: add error handling
    }
}