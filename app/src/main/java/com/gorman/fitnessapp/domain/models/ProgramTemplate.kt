package com.gorman.fitnessapp.domain.models

data class ProgramTemplate(
    val programId: String,
    val name: String,
    val description: String,
    val muscleGroup: String,
    val goalType: String
)
