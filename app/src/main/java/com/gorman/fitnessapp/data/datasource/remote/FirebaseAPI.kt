package com.gorman.fitnessapp.data.datasource.remote

import com.gorman.fitnessapp.data.models.firebase.ArticleFirebase
import com.gorman.fitnessapp.data.models.firebase.ExerciseFirebase
import com.gorman.fitnessapp.data.models.firebase.MealFirebase
import com.gorman.fitnessapp.data.models.firebase.MealPlanItemFirebase
import com.gorman.fitnessapp.data.models.firebase.MealPlanTemplateFirebase
import com.gorman.fitnessapp.data.models.firebase.ProgramExerciseFirebase
import com.gorman.fitnessapp.data.models.firebase.ProgramFirebase
import com.gorman.fitnessapp.data.models.firebase.UserFirebase
import com.gorman.fitnessapp.data.models.firebase.UserProgramFirebase
import com.gorman.fitnessapp.data.models.firebase.UserProgressFirebase
import com.gorman.fitnessapp.data.models.firebase.WorkoutHistoryFirebase
import com.gorman.fitnessapp.domain.models.UsersData

interface FirebaseAPI {
    suspend fun getExercises(): List<ExerciseFirebase>
    suspend fun getUser(email: String): UserFirebase?
    suspend fun findUserPrograms(userId: String): Map<String, UserProgramFirebase>
    suspend fun deleteAllUserPrograms(userPrograms: Map<String, UserProgramFirebase>)
    suspend fun insertProgram(program: ProgramFirebase): String?
    suspend fun insertProgramExercise(programExercise: List<ProgramExerciseFirebase>?, programId: String?)
    suspend fun insertUserProgram(program: UserProgramFirebase)
    suspend fun insertUserProgress(userProgress: UserProgressFirebase): String?
    suspend fun getUserProgress(userId: String): List<UserProgressFirebase>?
    suspend fun insertUser(user: UsersData): String?
    suspend fun deleteUser(user: UserFirebase)
    suspend fun updateUser(user: UserFirebase)
    suspend fun getMeals(): List<MealFirebase>
    suspend fun insertMealPlan(mealPlanItemFirebase: List<MealPlanItemFirebase>,
                               mealPlanTemplateFirebase: MealPlanTemplateFirebase,
                               userId: String): String?
    suspend fun findUserMealPlanTemplate(userId: String): Map<String, MealPlanTemplateFirebase>
    suspend fun deleteMealPlan(templateId: String)
    suspend fun getProgram(programId: String): ProgramFirebase?
    suspend fun getProgramExercises(programId: String): List<ProgramExerciseFirebase>
    suspend fun getUserProgram(userId: String): UserProgramFirebase?
    suspend fun getMealPlans(userId: String): Map<String, Pair<MealPlanTemplateFirebase, List<MealPlanItemFirebase>>>?
    suspend fun insertWorkoutHistory(workoutHistoryFirebase: WorkoutHistoryFirebase): String?
    suspend fun getWorkoutHistory(userId: String): List<WorkoutHistoryFirebase>
    suspend fun getArticles(): List<ArticleFirebase>
}