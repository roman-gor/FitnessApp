package com.gorman.fitnessapp.data.models.postgresql

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealPlanTemplateSupabase(
    @SerialName("id_mealplantemplate")
    val templateId: Int = 0,
    @SerialName("userid_usersdata")
    val userId: Int = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("description")
    val description: String? = null
)
