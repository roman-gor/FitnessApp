package com.gorman.fitnessapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gorman.fitnessapp.data.models.room.UserProgramEntity

@Dao
interface UserProgramDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProgram(userProgramEntity: UserProgramEntity)
    @Query("SELECT * FROM userprogram WHERE programId=:programId")
    suspend fun getUserProgramById(programId: Int): UserProgramEntity
    @Query("SELECT COUNT(*) FROM userprogram")
    suspend fun getUserProgramsCount(): Int
    @Query("DELETE FROM userprogram")
    suspend fun deleteUserProgram()
}