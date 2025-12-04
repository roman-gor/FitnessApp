package com.gorman.fitnessapp.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
import com.gorman.fitnessapp.data.models.room.ArticleEntity
import com.gorman.fitnessapp.data.models.room.ExerciseEntity
import com.gorman.fitnessapp.data.models.room.MealEntity
import com.gorman.fitnessapp.data.models.room.MealPlanItemEntity
import com.gorman.fitnessapp.data.models.room.MealPlanTemplateEntity
import com.gorman.fitnessapp.data.models.room.ProgramEntity
import com.gorman.fitnessapp.data.models.room.ProgramExerciseEntity
import com.gorman.fitnessapp.data.models.room.UserProgramEntity
import com.gorman.fitnessapp.data.models.room.UserProgressEntity
import com.gorman.fitnessapp.data.models.room.UsersDataEntity
import com.gorman.fitnessapp.data.models.room.WorkoutHistoryEntity

@Database(entities = [
    UsersDataEntity::class,
    ExerciseEntity::class,
    ProgramEntity::class,
    ProgramExerciseEntity::class,
    MealEntity::class,
    MealPlanTemplateEntity::class,
    MealPlanItemEntity::class,
    UserProgressEntity::class,
    UserProgramEntity::class,
    WorkoutHistoryEntity::class,
    ArticleEntity::class
], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun usersDataDao(): UsersDataDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun programDao(): ProgramDao
    abstract fun programExerciseDao(): ProgramExerciseDao
    abstract fun userProgramDao(): UserProgramDao
    abstract fun mealDao(): MealDao
    abstract fun mealPlanItemDao(): MealPlanItemDao
    abstract fun userProgressDao(): UserProgressDao
    abstract fun workoutHistoryDao(): WorkoutHistoryDao
    abstract fun mealPlanTemplateDao(): MealPlanTemplateDao
    abstract fun articleDao(): ArticleDao
}