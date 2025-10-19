package com.gorman.fitnessapp.domain.models

data class MealPlanItem(
    val localId: Int = 0,
    val firebaseId: String = "",
    val templateId: Int,
    val mealId: Int,
    val date: Long,
    val mealType: String,
    val quantity: Int = 1,
    val notes: String? = null
)
