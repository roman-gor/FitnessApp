package com.gorman.fitnessapp.domain.repository

import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.Meal
import com.gorman.fitnessapp.domain.models.MealPlanItem
import com.gorman.fitnessapp.domain.models.MealPlanTemplate
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.ProgramExercise
import com.gorman.fitnessapp.domain.models.UserProgram
import com.gorman.fitnessapp.domain.models.UsersData

interface FirebaseRepository {
    suspend fun getExercises(): List<Exercise>
    suspend fun getUser(email: String): UsersData?
    suspend fun findUserPrograms(userId: String): Map<String, UserProgram>
    suspend fun deleteAllUserPrograms(userPrograms: Map<String, UserProgram>)
    suspend fun insertProgram(program: Program): String?
    suspend fun insertProgramExercise(programExercise: List<ProgramExercise>?, programId: String?)
    suspend fun insertUserProgram(program: UserProgram)
    suspend fun insertUser(user: UsersData)
    suspend fun getMeals(): List<Meal>
    suspend fun insertMealPlan(mealPlanItem: List<MealPlanItem>,
                               mealPlanTemplate: MealPlanTemplate): String?
}