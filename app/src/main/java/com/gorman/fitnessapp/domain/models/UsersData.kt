package com.gorman.fitnessapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UsersData(
    val id: Int = 0,
    val firebaseId: String = "",
    val name: String? = null,
    val email: String? = null,
    val age: Long? = null,
    val goal: String? = null,
    val weight: Float? = null,
    val desiredWeight: Float? = null,
    val height: Float? = null,
    val gender: String? = null,
    val photoUrl: String? = null,
    val activityLevel: String? = null,
    val experienceLevel: String? = null
)
