package com.gorman.fitnessapp.domain.models

data class Exercise(
    val id: Int = 0,
    val supabaseId: Int = 0,
    val name: String,
    val description: String? = null,
    val muscleGroup: String,
    val complexity: Int? = null,
    val videoUrl: String? = null,
    val imageUrl: String? = null
)
