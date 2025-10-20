package com.gorman.fitnessapp.data.models.firebase

data class MealPlanItemFirebase(
    val id: String = "",
    val mealId: Int = 0,
    val date: Long = 0,
    val mealType: String = "",
    val quantity: Int = 1,
    val notes: String? = null
)
