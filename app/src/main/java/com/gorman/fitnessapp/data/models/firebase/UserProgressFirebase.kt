package com.gorman.fitnessapp.data.models.firebase

data class UserProgressFirebase(
    val id: String = "",
    val userId: String = "",
    val date: Long = 0,
    val weight: Float? = null,
    val caloriesBurned: Float? = null,
    val durationMinutes: Int? = null,
    val notes: String? = null
)
