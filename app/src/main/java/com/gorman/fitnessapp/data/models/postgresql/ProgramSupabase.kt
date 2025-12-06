package com.gorman.fitnessapp.data.models.postgresql

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProgramSupabase(
    @SerialName("program_id")
    val id: Int? = null,
    @SerialName("name")
    val name: String = "",
    @SerialName("muscle_group")
    val muscleGroup: String = "",
    @SerialName("description")
    val description: String? = null,
    @SerialName("goal_type")
    val goalType: String? = null
)
