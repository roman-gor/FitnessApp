package com.gorman.fitnessapp.domain.repository

import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.ProgramExercise
import com.gorman.fitnessapp.domain.models.UsersData

interface FirebaseRepository {
    suspend fun getExercises(): List<Exercise>
    suspend fun getUser(email: String): UsersData?
    suspend fun insertProgram(program: Program)
    suspend fun insertProgramExercise(programExercise: ProgramExercise, selectedProgramId: Int)
    suspend fun insertUser(user: UsersData)
}