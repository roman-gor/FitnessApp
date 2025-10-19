package com.gorman.fitnessapp.domain.models

data class MealPlan(
    val template: MealPlanTemplate,
    val items: List<MealPlanItem>
)
