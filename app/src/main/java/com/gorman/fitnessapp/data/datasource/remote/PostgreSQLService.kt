package com.gorman.fitnessapp.data.datasource.remote

import com.gorman.fitnessapp.data.models.postgresql.ExerciseSupabase
import com.gorman.fitnessapp.data.models.postgresql.MealSupabase
import com.gorman.fitnessapp.data.models.postgresql.MealPlanItemSupabase
import com.gorman.fitnessapp.data.models.postgresql.MealPlanTemplateSupabase
import com.gorman.fitnessapp.data.models.postgresql.ProgramExerciseSupabase
import com.gorman.fitnessapp.data.models.postgresql.ProgramSupabase
import com.gorman.fitnessapp.data.models.postgresql.UserSupabase
import com.gorman.fitnessapp.data.models.postgresql.UserProgramSupabase
import com.gorman.fitnessapp.domain.models.UsersData

interface PostgreSQLService {
    suspend fun getExercises(): List<ExerciseSupabase>
    suspend fun getUser(email: String): UserSupabase?
    suspend fun findUserPrograms(userId: Int): List<UserProgramSupabase>
    suspend fun deleteAllUserPrograms(userPrograms: List<UserProgramSupabase>)
    suspend fun insertProgram(program: ProgramSupabase): Int?
    suspend fun insertProgramExercise(programExercises: List<ProgramExerciseSupabase>?, programId: Int?)
    suspend fun insertUserProgram(program: UserProgramSupabase)
    suspend fun insertUser(user: UserSupabase): Int?
    suspend fun getMeals(): List<MealSupabase>
    suspend fun insertMealPlan(mealPlanItemSupabase: List<MealPlanItemSupabase>,
                               mealPlanTemplateSupabase: MealPlanTemplateSupabase,
                               userId: Int = 0): Int?
    suspend fun findUserMealPlanTemplate(userId: Int): Map<Int, MealPlanTemplateSupabase>
    suspend fun deleteMealPlan(templateId: Int)
    suspend fun getProgram(programId: Int): ProgramSupabase?
    suspend fun getProgramExercises(programId: Int): List<ProgramExerciseSupabase>
    suspend fun getUserProgram(userId: Int): UserProgramSupabase?
    suspend fun getMealPlans(userId: Int): Map<List<MealPlanItemSupabase>, MealPlanTemplateSupabase>
}