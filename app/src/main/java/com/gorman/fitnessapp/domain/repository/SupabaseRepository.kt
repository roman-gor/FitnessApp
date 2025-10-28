package com.gorman.fitnessapp.domain.repository

import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.Meal
import com.gorman.fitnessapp.domain.models.MealPlan
import com.gorman.fitnessapp.domain.models.MealPlanItem
import com.gorman.fitnessapp.domain.models.MealPlanTemplate
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.ProgramExercise
import com.gorman.fitnessapp.domain.models.ProgramOutput
import com.gorman.fitnessapp.domain.models.UserProgram
import com.gorman.fitnessapp.domain.models.UsersData

interface SupabaseRepository {
    suspend fun getExercises(): List<Exercise>
    suspend fun getUser(email: String): UsersData?
    suspend fun findUserPrograms(userId: Int): List<UserProgram>
    suspend fun deleteAllUserPrograms(userPrograms: List<UserProgram>)
    suspend fun insertProgram(program: Program): Int?
    suspend fun insertProgramExercise(programExercise: List<ProgramExercise>?, programId: Int?)
    suspend fun insertUserProgram(program: UserProgram)
    suspend fun insertUser(user: UsersData): Int?
    suspend fun getMeals(): List<Meal>
    suspend fun insertMealPlan(mealPlanItem: List<MealPlanItem>,
                               mealPlanTemplate: MealPlanTemplate,
                               userId: Int): Int?
    suspend fun findUserMealPlanTemplate(userId: Int): Map<Int, MealPlanTemplate>
    suspend fun deleteMealPlan(templateId: Int)
    suspend fun getProgram(userId: Int): ProgramOutput?
    suspend fun getMealPlans(userId: Int): MealPlan?
}