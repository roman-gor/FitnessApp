package com.gorman.fitnessapp.data.models.firebase

data class ProgramExerciseFirebase(
    val id: String = "",
    val exerciseId: Int = 0,
    val order: Int = 0,
    val dayOfWeek: String = "",
    val durationSec: Int = 0,
    val repetitions: Int = 0,
    val sets: Int = 0,
    val caloriesBurned: Float? = null
)
