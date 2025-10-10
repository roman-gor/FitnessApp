package com.gorman.fitnessapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "WorkoutHistory",
    indices = [
        Index(value = ["userId"]),
        Index(value = ["exerciseId"]),
        Index(value = ["programId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = UsersDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(entity = ExerciseEntity::class, parentColumns = ["id"], childColumns = ["exerciseId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = ProgramEntity::class, parentColumns = ["id"], childColumns = ["programId"], onDelete = ForeignKey.SET_NULL)
    ])
data class WorkoutHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val exerciseId: Int,
    val programId: Int? = null,
    val date: Long,
    val setsCompleted: Int,
    val repsCompleted: Int,
    val weightUsed: Float? = null,
    val isSuccessful: Boolean? = false
): Parcelable
