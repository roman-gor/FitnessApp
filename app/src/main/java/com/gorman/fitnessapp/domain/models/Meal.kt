package com.gorman.fitnessapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Meal(
    val localId: Int = 0,
    val supabaseId: Int = 0,
    val name: String,
    val description: String,
    val photo: String,
    val calories: Float,
    val protein: Float,
    val carbs: Float,
    val fats: Float,
    val recipe: String
)
