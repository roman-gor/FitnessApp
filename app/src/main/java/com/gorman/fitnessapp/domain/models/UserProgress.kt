package com.gorman.fitnessapp.domain.models

data class UserProgress(
    val localId: Int = 0,
    val supabaseId: Int = 0,
    val remoteUserId: Int,
    val date: Long,
    val weight: Float? = null,
    val caloriesBurned: Float? = null,
    val durationMinutes: Int? = null,
    val notes: String? = null
)
