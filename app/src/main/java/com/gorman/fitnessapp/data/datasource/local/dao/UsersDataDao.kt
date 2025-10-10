package com.gorman.fitnessapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gorman.fitnessapp.data.models.UsersDataEntity

@Dao
interface UsersDataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: UsersDataEntity)

    @Query("SELECT * FROM UsersData")
    suspend fun getAllUsers(): List<UsersDataEntity>
}