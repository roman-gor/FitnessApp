package com.gorman.fitnessapp.data.datasource.ai.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProgramExerciseDto(
    @SerialName("detail_id") val id: String,
    val exerciseId: Int,
    val order: Int,
    val dayOfWeek: String,
    val sets: Int,
    val repetitions: Int,
    val durationSec: Int,
    val caloriesBurned: Int
)
