package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.models.postgresql.MealSupabase
import com.gorman.fitnessapp.data.models.postgresql.MealPlanItemSupabase
import com.gorman.fitnessapp.data.models.postgresql.MealPlanTemplateSupabase
import com.gorman.fitnessapp.data.models.room.MealEntity
import com.gorman.fitnessapp.data.models.room.MealPlanItemEntity
import com.gorman.fitnessapp.data.models.room.MealPlanTemplateEntity
import com.gorman.fitnessapp.domain.models.Meal
import com.gorman.fitnessapp.domain.models.MealPlanItem
import com.gorman.fitnessapp.domain.models.MealPlanTemplate

fun Meal.toEntity(): MealEntity = MealEntity(
    supabaseId = supabaseId,
    name = name,
    description = description,
    calories = calories,
    protein = protein,
    carbs = carbs,
    fats = fats,
    recipe = recipe
)

fun MealEntity.toDomain(): Meal = Meal(
    localId = id,
    supabaseId = supabaseId,
    name = name,
    description = description,
    calories = calories,
    protein = protein,
    carbs = carbs,
    fats = fats,
    recipe = recipe
)

fun MealSupabase.toDomain(id: Int): Meal = Meal(
    supabaseId = id,
    name = name,
    description = description,
    calories = calories,
    protein = protein,
    carbs = carbs,
    fats = fats,
    recipe = recipe
)

fun MealPlanTemplate.toEntity(): MealPlanTemplateEntity = MealPlanTemplateEntity(
    supabaseId = supabaseId,
    name = name,
    description = description
)

fun MealPlanItem.toEntity(templateId: Int): MealPlanItemEntity = MealPlanItemEntity(
    supabaseId = supabaseId,
    templateId = templateId,
    mealId = mealId,
    mealType = mealType,
    date = date,
    quantity = quantity,
    notes = notes
)
fun MealPlanTemplate.toRemote(): MealPlanTemplateSupabase = MealPlanTemplateSupabase(
    userId = userSupabaseId,
    name = name,
    description = description
)
fun MealPlanItem.toRemote(): MealPlanItemSupabase = MealPlanItemSupabase(
    mealId = mealId,
    mealType = mealType,
    date = date,
    quantity = quantity,
    notes = notes
)

fun MealPlanTemplateSupabase.toDomain(supabaseId: Int): MealPlanTemplate = MealPlanTemplate(
    supabaseId = supabaseId,
    userSupabaseId = userId,
    name = name,
    description = description
)

fun MealPlanItemSupabase.toDomain(): MealPlanItem = MealPlanItem(
    supabaseId = templateId,
    mealId = mealId,
    mealType = mealType,
    date = date,
    quantity = quantity,
    notes = notes
)