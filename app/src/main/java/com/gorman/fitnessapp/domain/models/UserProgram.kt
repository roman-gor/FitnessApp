package com.gorman.fitnessapp.domain.models

data class UserProgram(
    val supabaseId: Int = 0,
    val userId: Int = 0,
    val programId: Int = 0,
    val startDate: Long,
    val endDate: Long? = null,
    val progress: Float? = null,
    val isCompleted: Boolean = false
)
