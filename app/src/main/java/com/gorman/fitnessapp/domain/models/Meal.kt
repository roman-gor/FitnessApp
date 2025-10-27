package com.gorman.fitnessapp.domain.models

data class Meal(
    val localId: Int = 0,
    val supabaseId: Int = 0,
    val name: String,
    val description: String,
    val calories: Float,
    val protein: Float,
    val carbs: Float,
    val fats: Float,
    val recipe: String
)
