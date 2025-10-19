package com.gorman.fitnessapp.data.models.firebase

data class MealFirebase(
    val id: String  = "",
    val name: String,
    val description: String,
    val calories: Float,
    val protein: Float,
    val carbs: Float,
    val fats: Float,
    val recipe: String
)
