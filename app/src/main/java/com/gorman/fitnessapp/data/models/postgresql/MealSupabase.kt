package com.gorman.fitnessapp.data.models.postgresql

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealSupabase(
    @SerialName("id_meal")
    val id: Int  = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("calories")
    val calories: Float = 0.0f,
    @SerialName("protein")
    val protein: Float = 0.0f,
    @SerialName("carbs")
    val carbs: Float = 0.0f,
    @SerialName("fats")
    val fats: Float = 0.0f,
    @SerialName("recipe")
    val recipe: String = ""
)
