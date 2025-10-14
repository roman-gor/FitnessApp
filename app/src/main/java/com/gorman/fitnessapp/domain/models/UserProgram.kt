package com.gorman.fitnessapp.domain.models

data class UserProgram(
    val userId: Int,
    val programId: Int,
    val startDate: Long,
    val endDate: Long? = null,
    val progress: Float? = null,
    val isCompleted: Boolean = false
)
