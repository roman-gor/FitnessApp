package com.gorman.fitnessapp.data.models.postgresql

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealPlanTemplateSupabase(
    @SerialName("template_id")
    val templateId: Int? = null,
    @SerialName("user_id")
    val userId: Int = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("description")
    val description: String? = null
)
