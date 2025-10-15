package com.gorman.fitnessapp.data.models.firebase

data class ProgramFirebase(
    val id: Int,
    val name: String,
    val muscleGroup: String,
    val description: String? = null,
    val goalType: String? = null
)
