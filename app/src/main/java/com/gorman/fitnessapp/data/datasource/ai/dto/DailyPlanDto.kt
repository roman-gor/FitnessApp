package com.gorman.fitnessapp.data.datasource.ai.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyPlanDto(
    @SerialName("dayOfWeek")
    val dayOfWeek: String,
    @SerialName("meals")
    val meals: List<MealItemDto>
)
