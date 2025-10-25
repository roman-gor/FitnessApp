package com.gorman.fitnessapp.domain.models

data class UserProgram(
    val firebaseId: String = "",
    val userId: String = "",
    val programId: Int = 0,
    val startDate: Long,
    val endDate: Long? = null,
    val progress: Float? = null,
    val isCompleted: Boolean = false
)
