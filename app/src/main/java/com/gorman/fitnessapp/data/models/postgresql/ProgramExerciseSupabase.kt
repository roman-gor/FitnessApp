package com.gorman.fitnessapp.data.models.postgresql

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProgramExerciseSupabase(
    @SerialName("id_programexercise")
    val id: Int = 0,
    @SerialName("programid_program")
    val programId: Int = 0,
    @SerialName("exerciseid_exercise")
    val exerciseId: Int = 0,
    @SerialName("ordering")
    val order: Int = 0,
    @SerialName("dayofweek")
    val dayOfWeek: String = "",
    @SerialName("durationsec")
    val durationSec: Int = 0,
    @SerialName("repetitions")
    val repetitions: Int = 0,
    @SerialName("sets")
    val sets: Int = 0,
    @SerialName("caloriesburned")
    val caloriesBurned: Float? = null
)
