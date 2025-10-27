package com.gorman.fitnessapp.domain.models

data class UsersData(
    val id: Int = 0,
    val supabaseId: Int = 0,
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
