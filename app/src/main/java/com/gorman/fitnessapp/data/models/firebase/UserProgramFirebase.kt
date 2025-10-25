package com.gorman.fitnessapp.data.models.firebase

data class UserProgramFirebase(
    val firebaseId: String = "",
    val userId: String = "",
    val programId: String = "",
    val startDate: Long = 0,
    val endDate: Long? = null,
    val progress: Float? = null,
    val isCompleted: Boolean = false
)
