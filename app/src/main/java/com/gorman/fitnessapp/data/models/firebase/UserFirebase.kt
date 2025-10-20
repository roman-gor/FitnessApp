package com.gorman.fitnessapp.data.models.firebase

data class UserFirebase(
    val userId: String = "",
    val name: String? = null,
    val email: String? = null,
    val birthday: Long? = null,
    val goal: String? = null,
    val weight: Float? = null,
    val desiredWeight: Float? = null,
    val height: Float? = null,
    val gender: String? = null,
    val photoUrl: String? = null,
    val activityLevel: String? = null,
    val experienceLevel: String? = null
)
