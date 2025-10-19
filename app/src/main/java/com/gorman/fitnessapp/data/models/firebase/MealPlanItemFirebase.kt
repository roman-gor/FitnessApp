package com.gorman.fitnessapp.data.models.firebase

data class MealPlanItemFirebase(
    val id: String = "",
    val mealId: Int,
    val date: Long,
    val mealType: String,
    val quantity: Int = 1,
    val notes: String? = null
)
