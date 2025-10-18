package com.gorman.fitnessapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gorman.fitnessapp.data.datasource.local.AppDatabase
import com.gorman.fitnessapp.data.datasource.local.dao.ExerciseDao
import com.gorman.fitnessapp.data.models.room.ExerciseEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ExerciseDaoTest {

    private lateinit var exerciseDao: ExerciseDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        exerciseDao = db.exerciseDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetExercises() = runTest {
        // Arrange
        val exercisesToInsert = listOf(
            ExerciseEntity(
                id = 1,
                name = "Push Up",
                description = "",
                muscleGroup = "",
                complexity = 1,
                videoUrl = "",
                imageUrl = ""
            ),
            ExerciseEntity(
                id = 2,
                name = "Push Up",
                description = "",
                muscleGroup = "",
                complexity = 2,
                videoUrl = "",
                imageUrl = ""
            )
        )

        // Act
        exerciseDao.insertExercises(exercisesToInsert)
        val retrievedExercises = exerciseDao.getExercises()

        // Assert
        Assert.assertEquals(exercisesToInsert, retrievedExercises)
    }
}