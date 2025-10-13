package com.gorman.fitnessapp.data.datasource.ai.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProgramDto(
    @SerialName("program_id") val programId: String,
    val name: String,
    val description: String,
    val muscleGroup: String,
    val goalType: String,
    @SerialName("program_exercise") val exercises: List<ProgramExerciseDto>
)