package com.gorman.fitnessapp.data.datasource.remote

import com.gorman.fitnessapp.data.models.firebase.ExerciseFirebase
import com.gorman.fitnessapp.data.models.firebase.MealFirebase
import com.gorman.fitnessapp.data.models.firebase.MealPlanItemFirebase
import com.gorman.fitnessapp.data.models.firebase.MealPlanTemplateFirebase
import com.gorman.fitnessapp.data.models.firebase.ProgramExerciseFirebase
import com.gorman.fitnessapp.data.models.firebase.ProgramFirebase
import com.gorman.fitnessapp.data.models.firebase.UserFirebase
import com.gorman.fitnessapp.data.models.firebase.UserProgramFirebase
import com.gorman.fitnessapp.domain.models.UsersData

interface FirebaseAPI {
    suspend fun getExercises(): List<ExerciseFirebase>
    suspend fun getUser(email: String): UserFirebase?
    suspend fun findUserPrograms(userId: String = "0"): Map<String, UserProgramFirebase>
    suspend fun deleteAllUserPrograms(userPrograms: Map<String, UserProgramFirebase>)
    suspend fun insertProgram(program: ProgramFirebase): String?
    suspend fun insertProgramExercise(programExercise: List<ProgramExerciseFirebase>?, programId: String?)
    suspend fun insertUserProgram(program: UserProgramFirebase)
    suspend fun insertUser(user: UsersData)
    suspend fun getMeals(): List<MealFirebase>
    suspend fun insertMealPlan(mealPlanItemFirebase: List<MealPlanItemFirebase>,
                               mealPlanTemplateFirebase: MealPlanTemplateFirebase): String?
    suspend fun getMealPlans(userId: Int): Map<List<MealPlanItemFirebase>, MealPlanTemplateFirebase>
}