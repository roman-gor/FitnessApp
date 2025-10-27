package com.gorman.fitnessapp.domain.models

data class MealPlanItem(
    val localId: Int = 0,
    val supabaseId: Int = 0,
    val templateId: Int = 0,
    val mealId: Int,
    val date: Long,
    val mealType: String,
    val quantity: Int = 1,
    val notes: String? = null
)
