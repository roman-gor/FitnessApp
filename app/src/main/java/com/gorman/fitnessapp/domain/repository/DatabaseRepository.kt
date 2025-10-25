package com.gorman.fitnessapp.domain.repository

import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.Meal
import com.gorman.fitnessapp.domain.models.MealPlan
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.ProgramExercise
import com.gorman.fitnessapp.domain.models.UserProgram
import com.gorman.fitnessapp.domain.models.UsersData

interface DatabaseRepository {
    suspend fun getAllUsers(): List<UsersData>
    suspend fun getUserProgramById(programId: Int): UserProgram
    suspend fun getListOfProgramExercises(programId: Int): List<ProgramExercise>
    suspend fun getUser(email: String?): UsersData
    suspend fun addUser(user: UsersData)
    suspend fun deleteUser(user: UsersData, id: Int)
    suspend fun updateUser(user: UsersData, id: Int): Int
    suspend fun getExercises(): List<Exercise>?
    suspend fun insertExercises(exercises: List<Exercise>)
    suspend fun insertProgramWithExercises(program: Program): Int
    suspend fun insertUserProgram(program: UserProgram)
    suspend fun getMeals(): List<Meal>
    suspend fun insertMeals(meals: List<Meal>)
    suspend fun insertMealsItems(meal: MealPlan)
    suspend fun getList(): List<ProgramExercise>
    suspend fun getProgramList(): List<Program>
}