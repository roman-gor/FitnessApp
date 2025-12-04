package com.gorman.fitnessapp.domain.models

data class Meal(
    val localId: Int = 0,
    val firebaseId: String  = "",
    val name: String,
    val description: String,
    val photo: String,
    val calories: Float,
    val protein: Float,
    val carbs: Float,
    val fats: Float,
    val recipe: String
)
