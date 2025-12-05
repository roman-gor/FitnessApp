package com.gorman.fitnessapp.data.models.postgresql

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProgressSupabase(
    @SerialName("progress_id")
    val id: Int = 0,
    @SerialName("user_id")
    val userId: Int,
    @SerialName("log_date")
    val date: Long,
    @SerialName("weight")
    val weight: Float? = null,
    @SerialName("calories_burned")
    val caloriesBurned: Float? = null,
    @SerialName("duration_minutes")
    val durationMinutes: Int? = null,
    @SerialName("notes")
    val notes: String? = null
)
