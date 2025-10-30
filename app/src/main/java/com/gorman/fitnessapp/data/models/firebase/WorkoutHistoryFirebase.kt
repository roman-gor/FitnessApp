package com.gorman.fitnessapp.data.models.firebase

data class WorkoutHistoryFirebase(
    val id: String = "",
    val userId: String = "",
    val exerciseId: Int = 0,
    val programId: String? = null,
    val date: Long,
    val setsCompleted: Int,
    val repsCompleted: Int,
    val weightUsed: Float? = null
)
