package com.gorman.fitnessapp.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.gorman.fitnessapp.data.models.room.ProgramExerciseEntity

@Dao
interface ProgramExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgramExercise(programExerciseEntity: List<ProgramExerciseEntity>)
    @Query("SELECT * FROM ProgramExercise WHERE programId=:programId")
    suspend fun getListOfProgramExercise(programId: Int): List<ProgramExerciseEntity>
    @Query("SELECT * FROM programexercise")
    suspend fun getList(): List<ProgramExerciseEntity>
    @Query("SELECT COUNT(*) FROM programexercise")
    suspend fun getProgramsExerciseCount(): Int
    @Query("DELETE FROM programexercise")
    suspend fun deleteAllRows()
}