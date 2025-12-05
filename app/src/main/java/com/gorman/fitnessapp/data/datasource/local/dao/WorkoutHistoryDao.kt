package com.gorman.fitnessapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.gorman.fitnessapp.data.models.room.UserProgressEntity
import com.gorman.fitnessapp.data.models.room.WorkoutHistoryEntity

@Dao
interface WorkoutHistoryDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertWorkoutHistory(workoutHistoryEntity: WorkoutHistoryEntity)
    @Insert(onConflict = REPLACE)
    suspend fun insertListWorkoutHistory(workoutHistoryEntities: List<WorkoutHistoryEntity>)
    @Query("SELECT * FROM workouthistory")
    suspend fun getWorkoutHistory(): List<WorkoutHistoryEntity>
    @Query("DELETE FROM workouthistory")
    suspend fun deleteWorkoutHistory()
}