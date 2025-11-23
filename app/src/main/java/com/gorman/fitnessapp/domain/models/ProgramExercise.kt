package com.gorman.fitnessapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class ProgramExercise(
    val id: Int = 0,
    val firebaseId: String = "",
    val exerciseId: Int,
    val order: Int,
    val dayOfWeek: String,
    val sets: Int,
    val repetitions: Int,
    val durationSec: Int,
    val caloriesBurned: Float?
)
