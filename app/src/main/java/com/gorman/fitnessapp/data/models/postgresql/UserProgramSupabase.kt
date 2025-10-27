package com.gorman.fitnessapp.data.models.postgresql

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProgramSupabase(
    @SerialName("userid_usersdata")
    val userId: Int = 0,
    @SerialName("programid_program")
    val programId: Int = 0,
    @SerialName("startdate")
    val startDate: Long = 0,
    @SerialName("enddate")
    val endDate: Long? = 0,
    @SerialName("progress")
    val progress: Float? = 0f,
    @SerializedName("iscompleted")
    val isCompleted: Boolean = false
)
