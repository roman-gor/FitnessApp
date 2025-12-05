package com.gorman.fitnessapp.data.repository

import android.util.Log
import com.gorman.fitnessapp.data.datasource.remote.PostgreSQLService
import com.gorman.fitnessapp.data.mapper.toDomain
import com.gorman.fitnessapp.data.mapper.toRemote
import com.gorman.fitnessapp.domain.models.Article
import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.Meal
import com.gorman.fitnessapp.domain.models.MealPlan
import com.gorman.fitnessapp.domain.models.MealPlanItem
import com.gorman.fitnessapp.domain.models.MealPlanTemplate
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.ProgramExercise
import com.gorman.fitnessapp.domain.models.ProgramOutput
import com.gorman.fitnessapp.domain.models.UserProgram
import com.gorman.fitnessapp.domain.models.UserProgress
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.models.WorkoutHistory
import com.gorman.fitnessapp.domain.repository.SupabaseRepository
import javax.inject.Inject

class SupabaseRepositoryImpl @Inject constructor(
    private val postgreSQLService: PostgreSQLService
): SupabaseRepository {
    override suspend fun getExercises(): List<Exercise> {
        return postgreSQLService.getExercises().map {
            it.toDomain()
        }
    }

    override suspend fun getUser(email: String): UsersData? {
        return postgreSQLService.getUser(email)?.toDomain()
    }

    override suspend fun findUserPrograms(userId: Int): List<UserProgram> {
        val remoteUserPrograms = postgreSQLService.findUserPrograms(userId)
        return remoteUserPrograms.map { it.toDomain() }
    }

    override suspend fun deleteAllUserPrograms(userPrograms: List<UserProgram>) {
        postgreSQLService.deleteAllUserPrograms(userPrograms.map { it.toRemote() })
    }

    override suspend fun insertProgram(program: Program): Int? {
        return postgreSQLService.insertProgram(program.toRemote())
    }

    override suspend fun insertProgramExercise(programExercise: List<ProgramExercise>?, programId: Int?) {
        programId?.let { id->
            postgreSQLService.insertProgramExercise(programExercise?.map { it.toRemote(id) }, id)
        }
    }

    override suspend fun insertUserProgram(program: UserProgram) {
        postgreSQLService.insertUserProgram(program.toRemote())
    }

    override suspend fun insertUserProgress(userProgress: UserProgress): Int? {
        return postgreSQLService.insertUserProgress(userProgress.toRemote())
    }

    override suspend fun updateUserProgress(userProgress: UserProgress) {
        postgreSQLService.updateUserProgress(userProgress.toRemote())
    }

    override suspend fun getUserProgress(userId: Int): List<UserProgress> {
        return postgreSQLService.getUserProgress(userId).map { it.toDomain() }
    }

    override suspend fun insertUser(user: UsersData): Int? {
        return postgreSQLService.insertUser(user.toRemote())
    }

    override suspend fun deleteUser(user: UsersData) {
        return postgreSQLService.deleteUser(user.toRemote())
    }

    override suspend fun updateUser(user: UsersData) {
        return postgreSQLService.updateUser(user.toRemote())
    }

    override suspend fun getMeals(): List<Meal> {
        return postgreSQLService.getMeals().map { it.toDomain(it.id) }
    }

    override suspend fun insertMealPlan(
        mealPlanItem: List<MealPlanItem>,
        mealPlanTemplate: MealPlanTemplate,
        userId: Int
    ): Int? {
        return postgreSQLService.insertMealPlan(
            mealPlanItem.map { it.toRemote() },
            mealPlanTemplate.toRemote(),
            userId)
    }

    override suspend fun findUserMealPlanTemplate(userId: Int): Map<Int, MealPlanTemplate> {
        val remoteTemplates = postgreSQLService.findUserMealPlanTemplate(userId)
        return remoteTemplates.mapValues { it.value.toDomain(remoteTemplates.keys.first()) }
    }

    override suspend fun deleteMealPlan(templateId: Int) {
        postgreSQLService.deleteMealPlan(templateId)
    }

    override suspend fun getProgram(userId: Int): ProgramOutput? {
        val userProgram = postgreSQLService.getUserProgram(userId)
            ?: return null
        val programDetails = userProgram.programId.let { postgreSQLService.getProgram(it) }
            ?: return null
        val programExercises = userProgram.programId.let { postgreSQLService.getProgramExercises(it) }
        val domainProgram = ProgramOutput(
            template = programDetails.toDomain()
                .copy(
                    supabaseId = userProgram.programId,
                    exercises = programExercises.map { it.toDomain() }),
            userProgram = userProgram.toDomain()
        )
        Log.d("FirebaseProgramId", domainProgram.template.toString())
        return domainProgram
    }

    override suspend fun getMealPlans(userId: Int): MealPlan? {
        val mealPlans = postgreSQLService.getMealPlans(userId)
        val firstPlan = mealPlans.firstOrNull() ?: return null
        val supabaseTemplateId = firstPlan.template.templateId
        return MealPlan(
            template = firstPlan.template.toDomain(supabaseTemplateId),
            items = firstPlan.items.map { it.toDomain() }
        )
    }

    override suspend fun insertWorkoutHistory(workoutHistory: WorkoutHistory): Int? {
        return postgreSQLService.insertWorkoutHistory(workoutHistory.toRemote())
    }

    override suspend fun updateWorkoutHistory(workoutHistory: WorkoutHistory) {
        postgreSQLService.updateWorkoutHistory(workoutHistory.toRemote())
    }

    override suspend fun getWorkoutHistory(userId: Int): List<WorkoutHistory> {
        return postgreSQLService.getWorkoutHistory(userId).map { it.toDomain() }
    }

    override suspend fun getArticles(): List<Article> {
        return postgreSQLService.getArticles().map { it.toDomain() }
    }
}