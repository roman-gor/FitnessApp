package com.gorman.fitnessapp.data.models.firebase

data class MealFirebase(
    val id: String  = "",
    val name: String = "",
    val description: String = "",
    val calories: Float = 0.0f,
    val protein: Float = 0.0f,
    val carbs: Float = 0.0f,
    val fats: Float = 0.0f,
    val recipe: String = ""
)
