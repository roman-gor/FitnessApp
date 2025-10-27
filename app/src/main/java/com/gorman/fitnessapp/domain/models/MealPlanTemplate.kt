package com.gorman.fitnessapp.domain.models

data class MealPlanTemplate(
    val localId: Int = 0,
    val supabaseId: Int = 0,
    val userId: Int = 0,
    val userSupabaseId: Int = 0,
    val name: String,
    val description: String? = null
)
