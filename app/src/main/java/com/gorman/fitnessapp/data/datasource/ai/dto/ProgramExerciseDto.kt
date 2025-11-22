package com.gorman.fitnessapp.data.datasource.ai.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProgramExerciseDto(
    @SerialName("detail_id") val id: String,
    val exerciseId: Int,
    val order: Int = 0,
    val dayOfWeek: String = "",
    val sets: Int = 3,
    val repetitions: Int = 4,
    val durationSec: Int = 240,
    val caloriesBurned: Int = 120
)
