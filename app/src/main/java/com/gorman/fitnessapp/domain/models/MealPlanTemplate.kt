package com.gorman.fitnessapp.domain.models

data class MealPlanTemplate(
    val localId: Int = 0,
    val firebaseId: String = "",
    val userId: Int = 0,
    val userFirebaseId: String = "",
    val name: String,
    val description: String? = null
)
