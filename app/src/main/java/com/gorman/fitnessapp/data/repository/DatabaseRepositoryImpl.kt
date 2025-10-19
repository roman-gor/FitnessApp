package com.gorman.fitnessapp.data.repository

import androidx.room.withTransaction
import com.gorman.fitnessapp.data.datasource.local.AppDatabase
import com.gorman.fitnessapp.data.datasource.local.dao.ExerciseDao
import com.gorman.fitnessapp.data.datasource.local.dao.MealDao
import com.gorman.fitnessapp.data.datasource.local.dao.MealPlanItemDao
import com.gorman.fitnessapp.data.datasource.local.dao.MealPlanTemplateDao
import com.gorman.fitnessapp.data.datasource.local.dao.ProgramDao
import com.gorman.fitnessapp.data.datasource.local.dao.ProgramExerciseDao
import com.gorman.fitnessapp.data.datasource.local.dao.UserProgramDao
import com.gorman.fitnessapp.data.datasource.local.dao.UsersDataDao
import com.gorman.fitnessapp.data.mapper.toDomain
import com.gorman.fitnessapp.data.mapper.toEntity
import com.gorman.fitnessapp.domain.models.Exercise
import com.gorman.fitnessapp.domain.models.Meal
import com.gorman.fitnessapp.domain.models.MealPlan
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.ProgramExercise
import com.gorman.fitnessapp.domain.models.UserProgram
import com.gorman.fitnessapp.domain.models.UsersData
import com.gorman.fitnessapp.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val usersDataDao: UsersDataDao,
    private val exerciseDao: ExerciseDao,
    private val userProgramDao: UserProgramDao,
    private val programExerciseDao: ProgramExerciseDao,
    private val programDao: ProgramDao,
    private val mealDao: MealDao,
    private val mealPlanTemplateDao: MealPlanTemplateDao,
    private val mealPlanItemDao: MealPlanItemDao
): DatabaseRepository {

    override suspend fun getAllUsers(): List<UsersData> {
        return usersDataDao.getAllUsers()
            .map { it.toDomain() }
    }

    override suspend fun getUserProgramById(userId: Int, programId: Int): UserProgram {
        return userProgramDao.getUserProgramById(userId = userId, programId = programId).toDomain()
    }

    override suspend fun getListOfProgramExercises(programId: Int): List<ProgramExercise> {
        return programExerciseDao.getListOfProgramExercise(programId).map { it.toDomain() }
    }

    override suspend fun getUser(email: String?): UsersData {
        return usersDataDao.getUserByEmail(email).toDomain()
    }

    override suspend fun addUser(user: UsersData) {
        return usersDataDao.addUser(user.toEntity())
    }

    override suspend fun deleteUser(user: UsersData, id: Int) {
        return usersDataDao.deleteUser(user.toEntity(id))
    }

    override suspend fun updateUser(user: UsersData, id: Int): Int {
        return usersDataDao.updateUser(user.toEntity(id))
    }

    override suspend fun getExercises(): List<Exercise> {
        return exerciseDao.getExercises().map { it.toDomain() }
    }

    override suspend fun insertExercises(exercises: List<Exercise>) {
        exerciseDao.insertExercises(exercises.map { it.toEntity() })
    }

    override suspend fun insertProgramWithExercises(programs: List<Program>, selectedProgramIndex: Int) {
        db.withTransaction {
            programs.forEachIndexed { index, program ->
                val programId = programDao.insertProgramTemplate(program.toEntity()).toInt()
                if (index == selectedProgramIndex) {
                    val exercisesEntity = program.exercises?.map { it.toEntity(programId) }
                    exercisesEntity?.let { programExerciseDao.insertProgramExercise(it) }
                }
            }
        }
    }

    override suspend fun getMeals(): List<Meal> {
        return mealDao.getMeals().map { it.toDomain() }
    }

    override suspend fun insertMeals(meals: List<Meal>) {
        mealDao.insertMeals(meals.map { it.toEntity() })
    }

    override suspend fun insertMealsItems(meal: MealPlan) {
        db.withTransaction {
            val templateId = mealPlanTemplateDao.insertMealPlanTemplate(meal.template.toEntity()).toInt()
            val mappedItems = meal.items.map { it.toEntity(templateId) }
            mealPlanItemDao.insertMealPlanItem(mappedItems)
        }
    }
}