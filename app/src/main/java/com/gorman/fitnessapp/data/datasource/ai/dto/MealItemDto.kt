package com.gorman.fitnessapp.data.datasource.ai.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealItemDto(
    @SerialName("mealId")
    val mealId: Int,

    @SerialName("mealType")
    val mealType: String,

    @SerialName("quantity")
    val quantity: Int,

    @SerialName("notes")
    val notes: String? = null
)
