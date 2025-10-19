package com.gorman.fitnessapp

import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DatabaseReference
import com.gorman.fitnessapp.data.datasource.remote.FirebaseAPIImpl
import com.gorman.fitnessapp.data.models.firebase.MealPlanItemFirebase
import com.gorman.fitnessapp.data.models.firebase.MealPlanTemplateFirebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FirebaseAPIImplTest {

    @Mock
    private lateinit var database: DatabaseReference

    // Mocks for chained calls
    @Mock
    private lateinit var mealPlanTemplateRef: DatabaseReference
    @Mock
    private lateinit var newMealPlanTemplateRef: DatabaseReference
    @Mock
    private lateinit var mealPlanItemsRef: DatabaseReference
    @Mock
    private lateinit var mealPlanItemsForTemplateRef: DatabaseReference
    @Mock
    private lateinit var newItemRef: DatabaseReference

    private lateinit var firebaseAPI: FirebaseAPIImpl

    @Before
    fun setUp() {
        // Mock the chain of calls for DatabaseReference
        whenever(database.child("meal_plan_templates")).thenReturn(mealPlanTemplateRef)
        whenever(mealPlanTemplateRef.push()).thenReturn(newMealPlanTemplateRef)
        whenever(newMealPlanTemplateRef.key).thenReturn("template_1")
        // FIX: Return an already completed Task to prevent await() from hanging
        whenever(newMealPlanTemplateRef.setValue(any())).thenReturn(Tasks.forResult(null))

        whenever(database.child("meal_plan_items")).thenReturn(mealPlanItemsRef)
        whenever(mealPlanItemsRef.child("template_1")).thenReturn(mealPlanItemsForTemplateRef)
        whenever(mealPlanItemsForTemplateRef.push()).thenReturn(newItemRef)
        // FIX: Return an already completed Task to prevent await() from hanging
        whenever(mealPlanItemsForTemplateRef.updateChildren(any())).thenReturn(Tasks.forResult(null))

        firebaseAPI = FirebaseAPIImpl(database)
    }

    @Test
    fun `insertMealPlan should write template and items to correct Firebase paths`() = runTest {
        // Arrange
        val template =
            MealPlanTemplateFirebase(userId = 123, name = "Test Template", description = "Desc")
        val items = listOf(
            MealPlanItemFirebase(
                mealId = 1,
                mealType = "Breakfast",
                templateId = 123,
                date = 17400000
            ),
            MealPlanItemFirebase(mealId = 2, mealType = "Lunch", templateId = 123, date = 17400000)
        )
        // Mock push keys for items
        whenever(newItemRef.key).thenReturn("item_1", "item_2")

        // Act
        firebaseAPI.insertMealPlan(items, template)

        // Assert: Verify template was inserted
        val templateCaptor = argumentCaptor<MealPlanTemplateFirebase>()
        verify(newMealPlanTemplateRef).setValue(templateCaptor.capture())
        assertEquals(template, templateCaptor.firstValue)

        // Assert: Verify items were inserted in a batch update
        val itemsMapCaptor = argumentCaptor<Map<String, Any>>()
        verify(mealPlanItemsForTemplateRef).updateChildren(itemsMapCaptor.capture())

        val capturedMap = itemsMapCaptor.firstValue
        assertEquals(2, capturedMap.size)
        assertEquals(items[0], capturedMap["item_1"])
        assertEquals(items[1], capturedMap["item_2"])
    }

    @Test
    fun `insertMealPlan with empty items should only write template`() = runTest {
        // Arrange
        val template =
            MealPlanTemplateFirebase(userId = 123, name = "Test Template", description = "Desc")
        val items = emptyList<MealPlanItemFirebase>()

        // Act
        firebaseAPI.insertMealPlan(items, template)

        // Assert: Verify template was inserted
        verify(newMealPlanTemplateRef).setValue(any())

        // Assert: Verify that no items were inserted
        verify(mealPlanItemsForTemplateRef, never()).updateChildren(any())
    }
}