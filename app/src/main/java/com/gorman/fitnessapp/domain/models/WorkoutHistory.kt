package com.gorman.fitnessapp.domain.models

data class WorkoutHistory(
    val localId: Int = 0,
    val firebaseId: String = "",
    val firebaseUserId: String,
    val exerciseId: Int,
    val programId: Int? = null,
    val remoteProgramId: String? = null,
    val date: Long,
    val setsCompleted: Int,
    val repsCompleted: Int,
    val weightUsed: Float? = null
)
