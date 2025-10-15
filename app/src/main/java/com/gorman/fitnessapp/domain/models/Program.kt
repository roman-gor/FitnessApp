package com.gorman.fitnessapp.domain.models

data class Program(
    val firebaseId: String = "",
    val localId: Int = 0,
    val name: String,
    val description: String? = null,
    val muscleGroup: String,
    val goalType: String? = null,
    val exercises: List<ProgramExercise>? = null
)
