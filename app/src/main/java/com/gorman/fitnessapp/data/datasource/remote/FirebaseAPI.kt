package com.gorman.fitnessapp.data.datasource.remote

import com.gorman.fitnessapp.data.models.firebase.ProgramExerciseFirebase
import com.gorman.fitnessapp.data.models.firebase.ProgramFirebase
import com.gorman.fitnessapp.data.models.firebase.UserFirebase
import com.gorman.fitnessapp.data.models.room.ExerciseEntity

interface FirebaseAPI {
    suspend fun getExercises(): List<ExerciseEntity>
    suspend fun getUser(email: String): UserFirebase?
    suspend fun insertProgram(program: ProgramFirebase)
    suspend fun insertProgramExercise(programExercise: ProgramExerciseFirebase)
    suspend fun insertUser(user: UserFirebase)
}