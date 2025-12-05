package com.gorman.fitnessapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class WorkoutHistory(
    val localId: Int = 0,
    val supabaseId: Int = 0,
    val remoteUserId: Int,
    val exerciseId: Int,
    val programId: Int? = null,
    val remoteProgramId: Int? = null,
    val date: Long,
    val setsCompleted: Int,
    val repsCompleted: Int,
    val weightUsed: Float? = null
)
