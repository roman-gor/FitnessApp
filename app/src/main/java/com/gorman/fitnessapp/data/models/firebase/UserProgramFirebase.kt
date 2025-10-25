package com.gorman.fitnessapp.data.models.firebase

import com.google.gson.annotations.SerializedName

data class UserProgramFirebase(
    val userId: String = "",
    val programId: String = "",
    val startDate: Long = 0,
    val endDate: Long? = 0,
    val progress: Float? = 0f,
    @SerializedName("completed")
    val isCompleted: Boolean = false
)
