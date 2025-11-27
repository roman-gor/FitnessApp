package com.gorman.fitnessapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.gorman.fitnessapp.data.models.room.UserProgressEntity

@Dao
interface UserProgressDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertUserProgress(userProgressEntity: UserProgressEntity)
    @Insert(onConflict = REPLACE)
    suspend fun insertListUserProgress(userProgressEntities: List<UserProgressEntity>)
    @Query("SELECT * FROM UserProgress")
    suspend fun getUserProgress(): List<UserProgressEntity>
}