package com.gorman.fitnessapp.data.models.postgresql

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProgressSupabase(
    @SerialName("id_userprogress")
    val id: Int = 0,
    @SerialName("userid_usersdata")
    val userId: Int,
    @SerialName("date")
    val date: Long,
    @SerialName("weight")
    val weight: Float? = null,
    @SerialName("caloriesburned")
    val caloriesBurned: Float? = null,
    @SerialName("durationminutes")
    val durationMinutes: Int? = null,
    @SerialName("notes")
    val notes: String? = null
)
