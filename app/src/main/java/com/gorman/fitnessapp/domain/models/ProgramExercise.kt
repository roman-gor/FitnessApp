package com.gorman.fitnessapp.domain.models

data class ProgramExercise(
    val id: Int = 0,
    val supabaseId: Int = 0,
    val exerciseId: Int,
    val order: Int,
    val dayOfWeek: String,
    val sets: Int,
    val repetitions: Int,
    val durationSec: Int,
    val caloriesBurned: Float?
)
