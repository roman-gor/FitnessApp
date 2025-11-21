package com.gorman.fitnessapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Exercise(
    val id: Int = 0,
    val firebaseId: String = "",
    val name: String,
    val description: String? = null,
    val muscleGroup: String,
    val complexity: Int? = null,
    val videoUrl: String? = null,
    val imageUrl: String? = null
)
