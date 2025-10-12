package com.gorman.fitnessapp.data.datasource.remote

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.getValue
import com.gorman.fitnessapp.data.models.ExerciseEntity
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAPIImpl @Inject constructor(
    private val database: DatabaseReference
) : FirebaseAPI {
    override suspend fun getExercises(): List<ExerciseEntity> {
        val exerciseRef = database.child("exercises")
        val dataSnapshot = exerciseRef.get().await()
        val exerciseList = mutableListOf<ExerciseEntity>()
        for (exerciseSnapshot in dataSnapshot.children) {
            val exercise = exerciseSnapshot.getValue<ExerciseEntity>()
            if (exercise != null) {
                exerciseList.add(exercise)
            }
        }
        return exerciseList
    }
}