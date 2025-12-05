package com.gorman.fitnessapp.data.models.postgresql

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkoutHistorySupabase(
    @SerialName("log_id")
    val id: Int = 0,
    @SerialName("user_id")
    val userId: Int,
    @SerialName("exercise_id")
    val exerciseId: Int,
    @SerialName("program_id")
    val programId: Int? = null,
    @SerialName("log_date")
    val date: Long,
    @SerialName("sets_completed")
    val setsCompleted: Int,
    @SerialName("reps_completed")
    val repsCompleted: Int,
    @SerialName("weight_used")
    val weightUsed: Float? = null
)
