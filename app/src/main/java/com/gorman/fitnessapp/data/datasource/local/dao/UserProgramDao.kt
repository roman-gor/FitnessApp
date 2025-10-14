package com.gorman.fitnessapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gorman.fitnessapp.data.models.UserProgramEntity

@Dao
interface UserProgramDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProgram(userProgramEntity: UserProgramEntity)
    @Query("SELECT * FROM userprogram WHERE userId=:userId AND programId=:programId")
    suspend fun getUserProgramById(userId: Int, programId: Int): UserProgramEntity
}