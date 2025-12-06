package com.gorman.fitnessapp.data.models.postgresql

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class ProgramExerciseSupabase(
    @Transient
    @SerialName("program_exercise_id")
    val id: Int? = null,
    @SerialName("program_id")
    val programId: Int = 0,
    @SerialName("exercise_id")
    val exerciseId: Int = 0,
    @SerialName("exercise_order")
    val order: Int = 0,
    @SerialName("day_of_week")
    val dayOfWeek: String = "",
    @SerialName("duration_sec")
    val durationSec: Int = 1,
    @SerialName("repetitions")
    val repetitions: Int = 0,
    @SerialName("sets")
    val sets: Int = 0,
    @SerialName("calories_burned")
    val caloriesBurned: Float = 0f
)
