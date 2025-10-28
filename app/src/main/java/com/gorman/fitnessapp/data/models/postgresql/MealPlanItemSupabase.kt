package com.gorman.fitnessapp.data.models.postgresql

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealPlanItemSupabase(
    @SerialName("id_mealplanitem")
    val itemId: Int = 0,
    @SerialName("templateid_mealplantemplate")
    val templateId: Int = 0,
    @SerialName("mealid_meal")
    val mealId: Int = 0,
    @SerialName("date")
    val date: Long = 0,
    @SerialName("mealtype")
    val mealType: String = "",
    @SerialName("quantity")
    val quantity: Int = 1,
    @SerialName("notes")
    val notes: String? = null
)
