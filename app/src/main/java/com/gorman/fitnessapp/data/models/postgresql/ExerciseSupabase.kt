package com.gorman.fitnessapp.data.models.postgresql

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseSupabase(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("description")
    val description: String? = null,
    @SerialName("muscle_group")
    val muscleGroup: String = "",
    @SerialName("complexity")
    val complexity: Int? = null,
    @SerialName("video_url")
    val videoUrl: String? = null,
    @SerialName("image_url")
    val imageUrl: String? = null
)
