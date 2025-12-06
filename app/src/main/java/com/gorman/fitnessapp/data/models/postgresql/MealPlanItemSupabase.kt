package com.gorman.fitnessapp.data.models.postgresql

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealPlanItemSupabase(
    @SerialName("meal_item_id")
    val itemId: Int? = null,
    @SerialName("template_id")
    val templateId: Int = 0,
    @SerialName("meal_id")
    val mealId: Int = 0,
    @SerialName("log_date")
    val date: Long = 0,
    @SerialName("meal_type")
    val mealType: String = "",
    @SerialName("quantity")
    val quantity: Int? = null,
    @SerialName("notes")
    val notes: String? = null
)
