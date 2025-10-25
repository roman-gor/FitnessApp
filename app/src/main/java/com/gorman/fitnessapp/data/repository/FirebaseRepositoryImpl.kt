package com.gorman.fitnessapp.data.repository

import com.gorman.fitnessapp.data.datasource.remote.FirebaseAPI
import com.gorman.fitnessapp.data.mapper.toDomain
import com.gorman.fitnessapp.data.mapper.toRemote
import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.Meal
import com.gorman.fitnessapp.domain.models.MealPlanItem
import com.gorman.fitnessapp.domain.models.MealPlanTemplate
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.ProgramExercise
import com.gorman.fitnessapp.domain.models.UserProgram
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

    override suspend fun findUserPrograms(userId: String): Map<String, UserProgram> {
        val remoteUserPrograms = firebaseAPI.findUserPrograms(userId)
        return remoteUserPrograms.mapValues { it.value.toDomain() }
    }

    override suspend fun deleteAllUserPrograms(userPrograms: Map<String, UserProgram>) {
        firebaseAPI.deleteAllUserPrograms(userPrograms.mapValues { it.value.toRemote() })
    }

    override suspend fun insertProgram(program: Program): String? {
        return firebaseAPI.insertProgram(program.toRemote())
    }

    override suspend fun insertProgramExercise(programExercise: List<ProgramExercise>?, programId: String?) {
        firebaseAPI.insertProgramExercise(programExercise?.map { it.toRemote() }, programId)
    }

    override suspend fun insertUserProgram(program: UserProgram) {
        firebaseAPI.insertUserProgram(program.toRemote())
    }

    override suspend fun insertUser(user: UsersData) {
        firebaseAPI.insertUser(user)
    }

    override suspend fun getMeals(): List<Meal> {
        return firebaseAPI.getMeals().map { it.toDomain(it.id) }
    }

    override suspend fun insertMealPlan(
        mealPlanItem: List<MealPlanItem>,
        mealPlanTemplate: MealPlanTemplate
    ): String? {
        return firebaseAPI.insertMealPlan(
            mealPlanItem.map { it.toRemote() },
            mealPlanTemplate.toRemote())
    }
}