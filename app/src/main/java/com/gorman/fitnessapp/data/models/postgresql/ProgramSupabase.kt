package com.gorman.fitnessapp.data.models.postgresql

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProgramSupabase(
    @SerialName("id_program")
    val id: Int = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("musclegroup")
    val muscleGroup: String = "",
    @SerialName("description")
    val description: String? = null,
    @SerialName("goaltype")
    val goalType: String? = null
)
