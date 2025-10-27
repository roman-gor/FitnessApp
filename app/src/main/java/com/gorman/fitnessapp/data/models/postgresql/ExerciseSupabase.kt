package com.gorman.fitnessapp.data.models.postgresql

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseSupabase(
    @SerialName("id_exercise")
    val id: Int = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("description")
    val description: String? = null,
    @SerialName("muscleGroup")
    val muscleGroup: String = "",
    @SerialName("complexity")
    val complexity: Int? = null,
    @SerialName("videoUrl")
    val videoUrl: String? = null,
    @SerialName("imageUrl")
    val imageUrl: String? = null
)
