package com.gorman.fitnessapp.data.models.firebase

data class ExerciseFirebase(
    val id: String = "",
    val name: String = "",
    val description: String? = null,
    val muscleGroup: String,
    val complexity: Int? = null,
    val videoUrl: String? = null,
    val imageUrl: String? = null
)
