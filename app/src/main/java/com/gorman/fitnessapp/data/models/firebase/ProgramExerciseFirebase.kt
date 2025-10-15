package com.gorman.fitnessapp.data.models.firebase

data class ProgramExerciseFirebase(
    val id: Int = 0,
    val programId: Int,
    val exerciseId: Int,
    val order: Int,
    val dayOfWeek: String,
    val durationSec: Int,
    val repetitions: Int,
    val sets: Int,
    val caloriesBurned: Float? = null
)
