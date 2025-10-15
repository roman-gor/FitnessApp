package com.gorman.fitnessapp.data.repository

import com.gorman.fitnessapp.data.datasource.remote.FirebaseAPI
import com.gorman.fitnessapp.data.mapper.toDomain
import com.gorman.fitnessapp.data.mapper.toEntity
import com.gorman.fitnessapp.data.mapper.toRemote
import com.gorman.fitnessapp.data.models.firebase.ProgramExerciseFirebase
import com.gorman.fitnessapp.data.models.firebase.ProgramFirebase
import com.gorman.fitnessapp.data.models.firebase.UserFirebase
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
        val user = firebaseAPI.getUser(email)?.toDomain()
        return user
    }

    override suspend fun insertProgram(program: Program) {
        firebaseAPI.insertProgram(program.toRemote())
    }

    override suspend fun insertProgramExercise(programExercise: ProgramExercise, selectedProgramId: Int) {
        firebaseAPI.insertProgramExercise(programExercise.toRemote(selectedProgramId))
    }

    override suspend fun insertUser(user: UsersData) {
        firebaseAPI.insertUser(user.toRemote())
    }
}