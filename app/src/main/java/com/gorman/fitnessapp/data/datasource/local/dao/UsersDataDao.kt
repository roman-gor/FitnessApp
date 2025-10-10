package com.gorman.fitnessapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gorman.fitnessapp.data.models.UsersDataEntity

@Dao
interface UsersDataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: UsersDataEntity)

    @Query("SELECT * FROM UsersData")
    suspend fun getAllUsers(): List<UsersDataEntity>

    @Query("SELECT * FROM UsersData WHERE email=:email")
    suspend fun getUserByEmail(email: String?): UsersDataEntity

    @Delete
    suspend fun deleteUser(user: UsersDataEntity)

    @Update
    suspend fun updateUser(user: UsersDataEntity): Int
}