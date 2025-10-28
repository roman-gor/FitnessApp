package com.gorman.fitnessapp.data.models.postgresql

data class MealPlanFullSupabase(
    val template: MealPlanTemplateSupabase,
    val items: List<MealPlanItemSupabase>
)
