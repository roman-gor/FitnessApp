package com.gorman.fitnessapp

import com.gorman.fitnessapp.data.mapper.toEntity
import com.gorman.fitnessapp.domain.models.MealPlanItem
import com.gorman.fitnessapp.domain.models.MealPlanTemplate
import org.junit.Assert
import org.junit.Test

class MealMapperTest {

    @Test
    fun `MealPlanTemplate toEntity mapping is correct`() {
        // Arrange
        val domainTemplate = MealPlanTemplate(
            localId = 1,
            firebaseId = "fb_template_1",
            userId = 123,
            name = "My Test Plan",
            description = "A plan for testing"
        )

        // Act
        val entity = domainTemplate.toEntity()

        // Assert
        Assert.assertEquals(domainTemplate.firebaseId, entity.firebaseId)
        Assert.assertEquals(domainTemplate.userId, entity.userId)
        Assert.assertEquals(domainTemplate.name, entity.name)
        Assert.assertEquals(domainTemplate.description, entity.description)
    }

    @Test
    fun `MealPlanItem toEntity mapping is correct`() {
        // Arrange
        val domainItem = MealPlanItem(
            localId = 1,
            firebaseId = "fb_item_1",
            templateId = 0, // Initial templateId, should be replaced
            mealId = 10,
            mealType = "Dinner",
            date = System.currentTimeMillis(),
            quantity = 2,
            notes = "Test notes"
        )
        val newTemplateId = 789

        // Act
        val entity = domainItem.toEntity(newTemplateId)

        // Assert
        Assert.assertEquals(domainItem.firebaseId, entity.firebaseId)
        Assert.assertEquals(newTemplateId, entity.templateId) // Check if the new templateId is used
        Assert.assertEquals(domainItem.mealId, entity.mealId)
        Assert.assertEquals(domainItem.mealType, entity.mealType)
        Assert.assertEquals(domainItem.date, entity.date)
        Assert.assertEquals(domainItem.quantity, entity.quantity)
        Assert.assertEquals(domainItem.notes, entity.notes)
    }
}