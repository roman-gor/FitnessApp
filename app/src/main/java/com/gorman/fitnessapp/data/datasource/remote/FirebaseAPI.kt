package com.gorman.fitnessapp.data.datasource.remote

import com.gorman.fitnessapp.data.models.firebase.ExerciseFirebase
import com.gorman.fitnessapp.data.models.firebase.ProgramExerciseFirebase
import com.gorman.fitnessapp.data.models.firebase.ProgramFirebase
import com.gorman.fitnessapp.data.models.firebase.UserFirebase
import com.gorman.fitnessapp.domain.models.UsersData

interface FirebaseAPI {
    suspend fun getExercises(): List<ExerciseFirebase>
    suspend fun getUser(email: String): UserFirebase?
    suspend fun insertProgram(program: ProgramFirebase): String?
    suspend fun insertProgramExercise(programExercise: List<ProgramExerciseFirebase>?, programId: String?)
    suspend fun insertUser(user: UsersData)
}