package com.gorman.fitnessapp

import com.gorman.fitnessapp.data.datasource.ai.dto.MealPlanDto
import com.gorman.fitnessapp.data.mapper.toDomain
import com.gorman.fitnessapp.domain.models.MealPlanTemplate
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

class MealJsonMapperTest {

    private val json = Json {
        ignoreUnknownKeys = true // To prevent crashes if the model and JSON are slightly different
        isLenient = true
    }

    // The JSON response from the neural network
    private val testJson = """
    {
      "planName": "План питания для набора мышечной массы (~3200 ккал)",
      "description": "Сбалансированный 7-дневный рацион, нацеленный на гипертрофию мышц. План обеспечивает профицит калорий и высокое содержание белка для стимуляции мышечного роста, исключая при этом молочные продукты. Рекомендуется пить достаточное количество воды (1.5-2 литра в день).",
      "goalType": "muscle_gain",
      "weeklyPlan": [
        {
          "dayOfWeek": "Понедельник",
          "meals": [
            { "mealId": 4, "mealType": "Завтрак", "quantity": 1, "notes": "Высокобелковый завтрак для начала дня." },
            { "mealId": 6, "mealType": "Обед", "quantity": 1, "notes": "Комплексный обед, богатый белком и медленными углеводами." },
            { "mealId": 9, "mealType": "Ужин", "quantity": 1, "notes": "" },
            { "mealId": 13, "mealType": "Перекус", "quantity": 1, "notes": "Отличный источник полезных жиров и белка." }
          ]
        },
        {
          "dayOfWeek": "Вторник",
          "meals": [
            { "mealId": 1, "mealType": "Завтрак", "quantity": 1, "notes": "Заряд энергии за счет сложных углеводов." },
            { "mealId": 2, "mealType": "Обед", "quantity": 1, "notes": "" },
            { "mealId": 5, "mealType": "Ужин", "quantity": 1, "notes": "Легкий, но насыщенный белком ужин." },
            { "mealId": 15, "mealType": "Перекус", "quantity": 1, "notes": "" }
          ]
        },
        {
          "dayOfWeek": "Пятница",
          "meals": [
            { "mealId": 1, "mealType": "Завтрак", "quantity": 1, "notes": "Добавьте больше ягод для антиоксидантов." },
            { "mealId": 6, "mealType": "Обед", "quantity": 1, "notes": "" },
            { "mealId": 5, "mealType": "Ужин", "quantity": 1, "notes": "Можно подавать с большим овощным салатом (ID 7)." },
            { "mealId": 10, "mealType": "Перекус", "quantity": 1, "notes": "Растительный белок и клетчатка." }
          ]
        }
      ]
    }
    """

    @Test
    fun `toDomain should correctly convert MealPlanDto to MealPlan`() {
        // Arrange
        val userId = 123
        val mealPlanDto = json.decodeFromString<MealPlanDto>(testJson)

        // Act
        val mealPlan = mealPlanDto.toDomain(userId)

        // Assert: Check the main template object
        val expectedTemplate = MealPlanTemplate(
            userId = userId,
            name = "План питания для набора мышечной массы (~3200 ккал)",
            description = "Сбалансированный 7-дневный рацион, нацеленный на гипертрофию мышц. План обеспечивает профицит калорий и высокое содержание белка для стимуляции мышечного роста, исключая при этом молочные продукты. Рекомендуется пить достаточное количество воды (1.5-2 литра в день)."
        )
        Assert.assertEquals(expectedTemplate, mealPlan.template)

        // Assert: Check the total number of meal items
        Assert.assertEquals(12, mealPlan.items.size) // 3 days * 4 meals each

        // Assert: Check a specific item to verify data and timestamp conversion
        val mondayBreakfast = mealPlan.items.first { it.mealId == 4 && it.mealType == "Завтрак" }
        val expectedMondayTimestamp = getTimestampForDay(DayOfWeek.MONDAY)

        Assert.assertEquals(4, mondayBreakfast.mealId)
        Assert.assertEquals("Завтрак", mondayBreakfast.mealType)
        Assert.assertEquals(expectedMondayTimestamp, mondayBreakfast.date)

        // Assert: Check another specific item from a different day
        val fridaySnack = mealPlan.items.first { it.mealId == 10 }
        val expectedFridayTimestamp = getTimestampForDay(DayOfWeek.FRIDAY)

        Assert.assertEquals(10, fridaySnack.mealId)
        Assert.assertEquals("Перекус", fridaySnack.mealType)
        Assert.assertEquals(expectedFridayTimestamp, fridaySnack.date)
    }

    // Helper function to replicate the timestamp logic from the mapper for verification
    private fun getTimestampForDay(dayOfWeek: DayOfWeek): Long {
        val today = LocalDate.now()
        val targetDate = today.with(TemporalAdjusters.nextOrSame(dayOfWeek))
        return targetDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}