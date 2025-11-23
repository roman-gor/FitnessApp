package com.gorman.fitnessapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gorman.fitnessapp.data.models.room.UserProgressEntity

@Dao
interface UserProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProgress(userProgressEntity: UserProgressEntity)
    @Query("SELECT * FROM UserProgress")
    suspend fun getUserProgress(): List<UserProgressEntity>
}