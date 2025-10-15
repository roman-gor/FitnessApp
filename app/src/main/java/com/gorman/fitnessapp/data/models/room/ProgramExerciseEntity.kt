package com.gorman.fitnessapp.data.models.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "ProgramExercise",
    indices = [
        Index(value = ["exerciseId"]),
        Index(value = ["programId", "exerciseId", "dayOfWeek"], unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = ProgramEntity::class,
            parentColumns = ["id"],
            childColumns = ["programId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(entity = ExerciseEntity::class, parentColumns = ["id"], childColumns = ["exerciseId"], onDelete = ForeignKey.CASCADE)
    ])
data class ProgramExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val firebaseId: String = "",
    val programId: Int,
    val exerciseId: Int,
    val order: Int,
    val dayOfWeek: String,
    val durationSec: Int,
    val repetitions: Int,
    val sets: Int,
    val caloriesBurned: Float? = null
): Parcelable
