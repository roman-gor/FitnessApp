package com.gorman.fitnessapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "ProgramExercise",
    primaryKeys = ["programId", "exerciseId"],
    indices = [
        Index(value = ["exerciseId"])
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
    val programId: Int,
    val exerciseId: Int,
    val order: Int? = null
): Parcelable
