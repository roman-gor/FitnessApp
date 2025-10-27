package com.gorman.fitnessapp.data.models.postgresql

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSupabase(
    @SerialName("id_usersdata")
    val userId: Int = 0,
    @SerialName("name")
    val name: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("birthday")
    val birthday: Long? = null,
    @SerialName("goal")
    val goal: String? = null,
    @SerialName("weight")
    val weight: Float? = null,
    @SerialName("desiredweight")
    val desiredWeight: Float? = null,
    @SerialName("height")
    val height: Float? = null,
    @SerialName("gender")
    val gender: String? = null,
    @SerialName("photourl")
    val photoUrl: String? = null,
    @SerialName("activitylevel")
    val activityLevel: String? = null,
    @SerialName("experiencelevel")
    val experienceLevel: String? = null
)
