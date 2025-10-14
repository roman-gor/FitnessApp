package com.gorman.fitnessapp.domain.repository

import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.ProgramExercise
import com.gorman.fitnessapp.domain.models.ProgramTemplate
import com.gorman.fitnessapp.domain.models.UserProgram
import com.gorman.fitnessapp.domain.models.UsersData

interface DatabaseRepository {
    suspend fun getAllUsers(): List<UsersData>
    suspend fun getUserProgramById(userId: Int, programId: Int): UserProgram
    suspend fun getListOfProgramExercises(programId: Int): List<ProgramExercise>
    suspend fun getUser(email: String?): UsersData
    suspend fun addUser(user: UsersData)
    suspend fun deleteUser(user: UsersData)
    suspend fun updateUser(user: UsersData): Int
    suspend fun getExercises(): List<Exercise>
    suspend fun insertExercises(exercises: List<Exercise>)
    suspend fun insertProgramExercise(programExercise: List<ProgramExercise>, programId: Int)
    suspend fun insertProgram(program: Program): Int
}