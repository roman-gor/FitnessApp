package com.gorman.fitnessapp.domain.repository

import com.gorman.fitnessapp.data.models.firebase.UserFirebase
import com.gorman.fitnessapp.data.models.firebase.UserProgressFirebase
import com.gorman.fitnessapp.data.models.firebase.WorkoutHistoryFirebase
import com.gorman.fitnessapp.domain.models.Article
import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.Meal
import com.gorman.fitnessapp.domain.models.MealPlanItem
import com.gorman.fitnessapp.domain.models.MealPlanTemplate
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.ProgramExercise
import com.gorman.fitnessapp.domain.models.ProgramOutput
import com.gorman.fitnessapp.domain.models.UserProgram
import com.gorman.fitnessapp.domain.models.UserProgress
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.models.WorkoutHistory

interface FirebaseRepository {
    suspend fun getExercises(): List<Exercise>
    suspend fun getUser(email: String): UsersData?
    suspend fun findUserPrograms(userId: String): Map<String, UserProgram>
    suspend fun deleteAllUserPrograms(userPrograms: Map<String, UserProgram>)
    suspend fun insertProgram(program: Program): String?
    suspend fun insertProgramExercise(programExercise: List<ProgramExercise>?, programId: String?)
    suspend fun insertUserProgram(program: UserProgram)
    suspend fun insertUserProgress(userProgress: UserProgress): String?
    suspend fun getUserProgress(userId: String): List<UserProgress>?
    suspend fun insertUser(user: UsersData): String?
    suspend fun deleteUser(user: UsersData)
    suspend fun updateUser(user: UsersData)
    suspend fun getMeals(): List<Meal>
    suspend fun insertMealPlan(mealPlanItem: List<MealPlanItem>,
                               mealPlanTemplate: MealPlanTemplate,
                               userId: String): String?
    suspend fun findUserMealPlanTemplate(userId: String): Map<String, MealPlanTemplate>
    suspend fun deleteMealPlan(templateId: String)
    suspend fun getProgram(userId: String): ProgramOutput?
    suspend fun getMealPlans(userId: String): Map<MealPlanTemplate, List<MealPlanItem>>?
    suspend fun insertWorkoutHistory(workoutHistory: WorkoutHistory): String?
    suspend fun getWorkoutHistory(userId: String): List<WorkoutHistory>
    suspend fun getArticles(): List<Article>
}