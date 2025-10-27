package com.gorman.fitnessapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gorman.fitnessapp.data.models.room.MealEntity

@Dao
interface MealDao {
    @Query("SELECT * FROM meal")
    suspend fun getMeals(): List<MealEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeals(meals: List<MealEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meals: MealEntity)
    @Query("SELECT COUNT(*) FROM meal")
    suspend fun getMealsCount(): Int
}