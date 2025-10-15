package com.gorman.fitnessapp.data.repository

import com.gorman.fitnessapp.data.datasource.remote.FirebaseAPI
import com.gorman.fitnessapp.data.mapper.toDomain
import com.gorman.fitnessapp.data.mapper.toRemote
import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.ProgramExercise
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.FirebaseRepository
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAPI: FirebaseAPI
): FirebaseRepository {
    override suspend fun getExercises(): List<Exercise> {
        return firebaseAPI.getExercises().map {
            it.toDomain()
        }
    }

    override suspend fun getUser(email: String): UsersData? {
        return firebaseAPI.getUser(email)?.toDomain()
    }

    override suspend fun insertProgram(program: Program): String? {
        return firebaseAPI.insertProgram(program.toRemote())
    }

    override suspend fun insertProgramExercise(programExercise: List<ProgramExercise>?, programId: String?) {
        firebaseAPI.insertProgramExercise(programExercise?.map { it.toRemote() }, programId)
    }

    override suspend fun insertUser(user: UsersData) {
        firebaseAPI.insertUser(user)
    }
}