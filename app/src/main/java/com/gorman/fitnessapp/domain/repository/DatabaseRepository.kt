package com.gorman.fitnessapp.domain.repository

import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.Meal
import com.gorman.fitnessapp.domain.models.MealPlan
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.ProgramExercise
import com.gorman.fitnessapp.domain.models.UserProgram
import com.gorman.fitnessapp.domain.models.UserProgress
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.models.WorkoutHistory

interface DatabaseRepository {
    suspend fun getUserProgramById(programId: Int): UserProgram
    suspend fun getListOfProgramExercises(programId: Int): List<ProgramExercise>
    suspend fun getUser(): UsersData
    suspend fun addUser(user: UsersData)
    suspend fun updateUser(user: UsersData, id: Int): Int
    suspend fun getExercises(): List<Exercise>?
    suspend fun insertExercises(exercises: List<Exercise>)
    suspend fun insertProgramWithExercises(program: Program): Int
    suspend fun insertUserProgram(program: UserProgram)
    suspend fun insertUserProgress(userProgress: UserProgress)
    suspend fun updateUserProgress(userProgress: UserProgress)
    suspend fun getUserProgress(): List<UserProgress>
    suspend fun getMeals(): List<Meal>
    suspend fun insertMeals(meals: List<Meal>)
    suspend fun insertMealsItems(meal: MealPlan)
    suspend fun getProgramList(): List<Program>
    suspend fun insertWorkoutHistory(workoutHistory: WorkoutHistory)
    suspend fun updateWorkoutHistory(workoutHistory: WorkoutHistory)
    suspend fun getWorkoutHistory(): List<WorkoutHistory>
}