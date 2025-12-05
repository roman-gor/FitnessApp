package com.gorman.fitnessapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class MealPlanTemplate(
    val localId: Int = 0,
    val supabaseId: Int = 0,
    val userId: Int = 0,
    val userSupabaseId: Int = 0,
    val name: String,
    val description: String? = null
)
