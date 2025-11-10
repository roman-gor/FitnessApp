package com.gorman.fitnessapp.data.repository

import android.util.Log
import androidx.room.Transaction
import com.gorman.fitnessapp.data.datasource.local.dao.ArticleDao
import com.gorman.fitnessapp.data.datasource.local.dao.ExerciseDao
import com.gorman.fitnessapp.data.datasource.local.dao.MealDao
import com.gorman.fitnessapp.data.datasource.local.dao.MealPlanItemDao
import com.gorman.fitnessapp.data.datasource.local.dao.MealPlanTemplateDao
import com.gorman.fitnessapp.data.datasource.local.dao.ProgramDao
import com.gorman.fitnessapp.data.datasource.local.dao.ProgramExerciseDao
import com.gorman.fitnessapp.data.datasource.local.dao.UserProgramDao
import com.gorman.fitnessapp.data.datasource.local.dao.UserProgressDao
import com.gorman.fitnessapp.data.datasource.local.dao.UsersDataDao
import com.gorman.fitnessapp.data.datasource.local.dao.WorkoutHistoryDao
import com.gorman.fitnessapp.data.mapper.toDomain
import com.gorman.fitnessapp.data.mapper.toEntity
import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.Meal
import com.gorman.fitnessapp.domain.models.MealPlan
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.ProgramExercise
import com.gorman.fitnessapp.domain.models.UserProgram
import com.gorman.fitnessapp.domain.models.UserProgress
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.models.WorkoutHistory
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val usersDataDao: UsersDataDao,
    private val exerciseDao: ExerciseDao,
    private val userProgramDao: UserProgramDao,
    private val programExerciseDao: ProgramExerciseDao,
    private val programDao: ProgramDao,
    private val mealDao: MealDao,
    private val mealPlanTemplateDao: MealPlanTemplateDao,
    private val mealPlanItemDao: MealPlanItemDao,
    private val userProgressDao: UserProgressDao,
    private val workoutHistoryDao: WorkoutHistoryDao,
    private val articleDao: ArticleDao
): DatabaseRepository {
    override suspend fun getUserProgramById(programId: Int): UserProgram {
        return userProgramDao.getUserProgramById(programId = programId).toDomain()
    }

    override suspend fun getListOfProgramExercises(programId: Int): List<ProgramExercise> {
        return programExerciseDao.getListOfProgramExercise(programId).map { it.toDomain() }
    }

    override suspend fun getUser(): UsersData {
        return usersDataDao.getUser().toDomain()
    }

    override suspend fun addUser(user: UsersData) {
        if (usersDataDao.getUsersCount() > 0)
            usersDataDao.deleteUser()
        return usersDataDao.addUser(user.toEntity())
    }

    override suspend fun updateUser(user: UsersData, id: Int): Int {
        return usersDataDao.updateUser(user.toEntity(id))
    }

    override suspend fun getExercises(): List<Exercise>? {
        return exerciseDao.getExercises().map { it.toDomain() }
    }

    @Transaction
    override suspend fun insertExercises(exercises: List<Exercise>) {
        if (exerciseDao.getExerciseCount() == 0)
            exerciseDao.insertExercises(exercises.map { it.toEntity() })
    }

    @Transaction
    override suspend fun insertProgramWithExercises(program: Program): Int {
        programDao.deleteAllRows()
        programExerciseDao.deleteAllRows()
        val programId = programDao.insertProgramTemplate(program.toEntity()).toInt()
        val exercisesEntity = program.exercises?.map { it.toEntity(programId) }
        exercisesEntity?.let { programExerciseDao.insertProgramExercise(it) }
        Log.d("ProgramSize", programDao.getProgramsCount().toString())
        return programId
    }

    override suspend fun insertUserProgram(program: UserProgram) {
        if (userProgramDao.getUserProgramsCount() != 0)
            userProgramDao.deleteUserProgram()
        userProgramDao.insertUserProgram(program.toEntity())
        Log.d("UserProgram Count", userProgramDao.getUserProgramsCount().toString())
    }

    override suspend fun insertUserProgress(userProgress: UserProgress) {
        userProgressDao.insertUserProgress(userProgress.toEntity())
    }

    override suspend fun updateUserProgress(userProgress: UserProgress) {
        userProgressDao.updateUserProgress(userProgress.toEntity())
    }

    override suspend fun getUserProgress(): List<UserProgress> {
        return userProgressDao.getUserProgress().map { it.toDomain() }
    }

    override suspend fun getMeals(): List<Meal> {
        return mealDao.getMeals().map { it.toDomain() }
    }

    override suspend fun insertMeals(meals: List<Meal>) {
        if (mealDao.getMealsCount() == 0)
            mealDao.insertMeals(meals.map { it.toEntity() })
    }

    @Transaction
    override suspend fun insertMealsItems(meal: MealPlan) {
        mealPlanItemDao.deleteAllRows()
        mealPlanTemplateDao.deleteAllRows()
        val templateId = mealPlanTemplateDao.insertMealPlanTemplate(meal.template.toEntity()).toInt()
        val mappedItems = meal.items.map { it.toEntity(templateId) }
        mealPlanItemDao.insertMealPlanItem(mappedItems)
        Log.d("MealsPlansCount", mealPlanTemplateDao.getMealsTemplateCount().toString())
    }

    override suspend fun getProgramList(): List<Program> {
        return programDao.getList().map{ it.toDomain() }
    }

    override suspend fun insertWorkoutHistory(workoutHistory: WorkoutHistory) {
        workoutHistoryDao.insertWorkoutHistory(workoutHistory.toEntity())
    }

    override suspend fun updateWorkoutHistory(workoutHistory: WorkoutHistory) {
        workoutHistoryDao.updateWorkoutHistory(workoutHistory.toEntity())
    }

    override suspend fun getWorkoutHistory(): List<WorkoutHistory> {
        return workoutHistoryDao.getWorkoutHistory().map { it.toDomain() }
    }
}