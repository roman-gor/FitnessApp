package com.gorman.fitnessapp.data.models.postgresql

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProgramSupabase(
    @SerialName("user_program_id")
    val id: Int = 0,
    @SerialName("user_id")
    val userId: Int = 0,
    @SerialName("program_id")
    val programId: Int = 0,
    @SerialName("start_date")
    val startDate: Long = 0,
    @SerialName("end_date")
    val endDate: Long? = 0,
    @SerialName("progress")
    val progress: Float? = 0f,
    @SerializedName("completed")
    val isCompleted: Boolean = false
)
