package com.gorman.fitnessapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.gorman.fitnessapp.data.models.room.UsersDataEntity

@Dao
interface UsersDataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: UsersDataEntity)
    @Query("SELECT * FROM UsersData LIMIT(1)")
    suspend fun getUser(): UsersDataEntity
    @Query("SELECT COUNT(*) FROM UsersData")
    suspend fun getUsersCount(): Int
    @Query("DELETE FROM usersdata")
    suspend fun deleteUser()
    @Update
    suspend fun updateUser(user: UsersDataEntity): Int
}