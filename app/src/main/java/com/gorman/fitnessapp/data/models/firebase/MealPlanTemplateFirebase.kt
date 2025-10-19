package com.gorman.fitnessapp.data.models.firebase

data class MealPlanTemplateFirebase(
    val id: String = "",
    val userId: Int,
    val name: String,
    val description: String? = null
)
