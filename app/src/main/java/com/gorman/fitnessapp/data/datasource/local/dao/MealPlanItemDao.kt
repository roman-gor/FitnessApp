package com.gorman.fitnessapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gorman.fitnessapp.data.models.room.MealPlanItemEntity

@Dao
interface MealPlanItemDao {
    @Query("SELECT * FROM mealplanitem")
    suspend fun getMealPlanItems(): List<MealPlanItemEntity>
    @Query("SELECT * FROM mealplanitem WHERE templateId=:templateId")
    suspend fun getMealPlanItemByTemplateId(templateId: Int): MealPlanItemEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealPlanItem(mealPlanItemEntity: List<MealPlanItemEntity>)
    @Query("DELETE FROM mealplanitem")
    suspend fun deleteAllRows()
}