package com.gorman.fitnessapp.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gorman.fitnessapp.data.datasource.local.dao.ExerciseDao
import com.gorman.fitnessapp.data.datasource.local.dao.UsersDataDao
import com.gorman.fitnessapp.data.models.ExerciseEntity
import com.gorman.fitnessapp.data.models.MealEntity
import com.gorman.fitnessapp.data.models.MealPlanItemEntity
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
    MealPlanItemEntity::class,
    UserProgressEntity::class,
    UserProgramEntity::class,
    WorkoutHistoryEntity::class
], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun usersDataDao(): UsersDataDao
    abstract fun exerciseDao(): ExerciseDao
}