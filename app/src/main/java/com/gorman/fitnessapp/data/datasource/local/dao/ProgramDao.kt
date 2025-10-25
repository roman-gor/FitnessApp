package com.gorman.fitnessapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.gorman.fitnessapp.data.models.room.ProgramEntity

@Dao
interface ProgramDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgramTemplate(programEntity: ProgramEntity): Long
    @Query("SELECT * FROM program")
    suspend fun getList(): List<ProgramEntity>
    @Query("SELECT COUNT(*) FROM program")
    suspend fun getProgramsCount(): Int
    @Query("DELETE FROM program")
    suspend fun deleteAllRows()
}