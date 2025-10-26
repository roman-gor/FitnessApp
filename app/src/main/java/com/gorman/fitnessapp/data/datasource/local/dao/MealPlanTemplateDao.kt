package com.gorman.fitnessapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gorman.fitnessapp.data.models.room.MealPlanTemplateEntity

@Dao
interface MealPlanTemplateDao {
    @Query("SELECT * FROM mealplantemplate")
    suspend fun getMealPlanTemplates(): List<MealPlanTemplateEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealPlanTemplate(meals: MealPlanTemplateEntity): Long
    @Query("DELETE FROM mealplantemplate")
    suspend fun deleteAllRows()
    @Query("SELECT * FROM mealplantemplate WHERE userId=:userId")
    suspend fun getMealPlanTemplateByUserId(userId: Int): MealPlanTemplateEntity
}