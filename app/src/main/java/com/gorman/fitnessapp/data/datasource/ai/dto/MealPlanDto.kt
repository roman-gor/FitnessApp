package com.gorman.fitnessapp.data.datasource.ai.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealPlanDto(
    @SerialName("planName")
    val planName: String,
    @SerialName("description")
    val description: String,
    @SerialName("goalType")
    val goalType: String,
    @SerialName("weeklyPlan")
    val weeklyPlan: List<DailyPlanDto>
)
