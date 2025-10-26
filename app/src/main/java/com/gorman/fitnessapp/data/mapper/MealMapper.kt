package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.models.firebase.MealFirebase
import com.gorman.fitnessapp.data.models.firebase.MealPlanItemFirebase
import com.gorman.fitnessapp.data.models.firebase.MealPlanTemplateFirebase
import com.gorman.fitnessapp.data.models.room.MealEntity
import com.gorman.fitnessapp.data.models.room.MealPlanItemEntity
import com.gorman.fitnessapp.data.models.room.MealPlanTemplateEntity
import com.gorman.fitnessapp.domain.models.Meal
import com.gorman.fitnessapp.domain.models.MealPlanItem
import com.gorman.fitnessapp.domain.models.MealPlanTemplate

fun Meal.toEntity(): MealEntity = MealEntity(
    firebaseId = firebaseId,
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
    firebaseId = firebaseId,
    name = name,
    description = description,
    calories = calories,
    protein = protein,
    carbs = carbs,
    fats = fats,
    recipe = recipe
)

fun MealFirebase.toDomain(id: String): Meal = Meal(
    firebaseId = id,
    name = name,
    description = description,
    calories = calories,
    protein = protein,
    carbs = carbs,
    fats = fats,
    recipe = recipe
)

fun MealPlanTemplate.toEntity(): MealPlanTemplateEntity = MealPlanTemplateEntity(
    firebaseId = firebaseId,
    userId = userId,
    name = name,
    description = description
)

fun MealPlanItem.toEntity(templateId: Int): MealPlanItemEntity = MealPlanItemEntity(
    firebaseId = firebaseId,
    templateId = templateId,
    mealId = mealId,
    mealType = mealType,
    date = date,
    quantity = quantity,
    notes = notes
)
fun MealPlanTemplate.toRemote(): MealPlanTemplateFirebase = MealPlanTemplateFirebase(
    userId = userId,
    name = name,
    description = description
)
fun MealPlanItem.toRemote(): MealPlanItemFirebase = MealPlanItemFirebase(
    mealId = mealId,
    mealType = mealType,
    date = date,
    quantity = quantity,
    notes = notes
)

fun MealPlanTemplateFirebase.toDomain(): MealPlanTemplate = MealPlanTemplate(
    firebaseId = id,
    userId = userId,
    name = name,
    description = description
)

fun MealPlanItemFirebase.toDomain(): MealPlanItem = MealPlanItem(
    firebaseId = id,
    mealId = mealId,
    mealType = mealType,
    date = date,
    quantity = quantity,
    notes = notes
)