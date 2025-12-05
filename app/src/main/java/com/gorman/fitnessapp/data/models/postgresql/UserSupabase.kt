package com.gorman.fitnessapp.data.models.postgresql

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSupabase(
    @SerialName("user_id")
    val userId: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("birthday")
    val age: Long? = null,
    @SerialName("goal")
    val goal: String? = null,
    @SerialName("weight")
    val weight: Float? = null,
    @SerialName("desired_weight")
    val desiredWeight: Float? = null,
    @SerialName("height")
    val height: Float? = null,
    @SerialName("gender")
    val gender: String? = null,
    @SerialName("photo_url")
    val photoUrl: String? = null,
    @SerialName("activity_level")
    val activityLevel: String? = null,
    @SerialName("experience_level")
    val experienceLevel: String? = null
)
