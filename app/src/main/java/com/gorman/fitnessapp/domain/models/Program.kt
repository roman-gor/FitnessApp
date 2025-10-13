package com.gorman.fitnessapp.domain.models

data class Program(
    val programId: String,
    val name: String,
    val description: String,
    val muscleGroup: String,
    val goalType: String,
    val exercises: List<ProgramExercise>
)
