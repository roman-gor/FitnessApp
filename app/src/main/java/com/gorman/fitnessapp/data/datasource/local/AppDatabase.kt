package com.gorman.fitnessapp.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gorman.fitnessapp.data.datasource.local.dao.ExerciseDao
import com.gorman.fitnessapp.data.datasource.local.dao.UsersDataDao
import com.gorman.fitnessapp.data.models.ExerciseEntity
import com.gorman.fitnessapp.data.models.MealEntity
import com.gorman.fitnessapp.data.models.MealPlanItemEntity
import com.gorman.fitnessapp.data.models.MealPlanTemplateEntity
import com.gorman.fitnessapp.data.models.ProgramEntity
import com.gorman.fitnessapp.data.models.ProgramExerciseEntity
import com.gorman.fitnessapp.data.models.UserProgramEntity
import com.gorman.fitnessapp.data.models.UserProgressEntity
import com.gorman.fitnessapp.data.models.UsersDataEntity
import com.gorman.fitnessapp.data.models.WorkoutHistoryEntity

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
    WorkoutHistoryEntity::class
], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun usersDataDao(): UsersDataDao
    abstract fun exerciseDao(): ExerciseDao
    companion object {
        val MIGRATION_CALLBACK = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL(CREATE_TRIGGER_PROGRAM_PROGRESS)
                db.execSQL(CREATE_TRIGGER_USER_WEIGHT)
                db.execSQL(CREATE_TRIGGER_PROGRAM_COMPLETION)
            }
        }
    }
}