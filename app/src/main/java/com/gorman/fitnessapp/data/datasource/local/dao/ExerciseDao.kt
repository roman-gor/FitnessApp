package com.gorman.fitnessapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gorman.fitnessapp.data.models.room.ExerciseEntity

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise")
    suspend fun getExercises(): List<ExerciseEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<ExerciseEntity>)
}