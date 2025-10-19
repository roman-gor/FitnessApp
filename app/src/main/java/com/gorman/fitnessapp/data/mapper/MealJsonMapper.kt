package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.datasource.ai.dto.MealPlanDto
import com.gorman.fitnessapp.domain.models.MealPlan
import com.gorman.fitnessapp.domain.models.MealPlanItem
import com.gorman.fitnessapp.domain.models.MealPlanTemplate
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

fun MealPlanDto.toDomain(userId: Int): MealPlan {
    val template = MealPlanTemplate(
        userId = userId,
        name = planName,
        description = description,
    )

    val items = weeklyPlan.flatMap { dailyPlan ->
        val dateTimestamp = convertDayOfWeekToTimestamp(dailyPlan.dayOfWeek)
        dailyPlan.meals.map { mealItemDto ->
            MealPlanItem(
                templateId = 0,
                mealId = mealItemDto.mealId,
                mealType = mealItemDto.mealType,
                quantity = mealItemDto.quantity,
                notes = mealItemDto.notes,
                date = dateTimestamp
            )
        }
    }
    return MealPlan(template = template, items = items)
}

private fun convertDayOfWeekToTimestamp(dayOfWeekString: String): Long {
    val targetDay = when (dayOfWeekString.lowercase()) {
        "понедельник" -> DayOfWeek.MONDAY
        "вторник" -> DayOfWeek.TUESDAY
        "среда" -> DayOfWeek.WEDNESDAY
        "четверг" -> DayOfWeek.THURSDAY
        "пятница" -> DayOfWeek.FRIDAY
        "суббота" -> DayOfWeek.SATURDAY
        "воскресенье" -> DayOfWeek.SUNDAY
        else -> throw IllegalArgumentException("Unknown day of week: $dayOfWeekString")
    }

    val today = LocalDate.now()
    val targetDate = today.with(TemporalAdjusters.nextOrSame(targetDay))

    return targetDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}