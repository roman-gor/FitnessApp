package com.gorman.fitnessapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gorman.fitnessapp.data.models.room.WorkoutHistoryEntity

@Dao
interface WorkoutHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutHistory(workoutHistoryEntity: WorkoutHistoryEntity)
    @Query("SELECT * FROM workouthistory")
    suspend fun getWorkoutHistory(): List<WorkoutHistoryEntity>
    @Update(WorkoutHistoryEntity::class)
    suspend fun updateWorkoutHistory(workoutHistoryEntity: WorkoutHistoryEntity)
}