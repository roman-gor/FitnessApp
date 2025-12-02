package com.gorman.fitnessapp.data.models.firebase

data class WorkoutHistoryFirebase(
    val id: String = "",
    val userId: String = "",
    val exerciseId: Int = 0,
    val programId: String? = null,
    val date: Long = 0,
    val setsCompleted: Int = 0,
    val repsCompleted: Int = 0,
    val weightUsed: Float? = null
)
