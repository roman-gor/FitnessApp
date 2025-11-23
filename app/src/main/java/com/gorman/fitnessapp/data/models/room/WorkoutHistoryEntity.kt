package com.gorman.fitnessapp.data.models.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "WorkoutHistory",
    indices = [
        Index(value = ["exerciseId"])
    ],
    foreignKeys = [
        ForeignKey(entity = ExerciseEntity::class, parentColumns = ["id"], childColumns = ["exerciseId"], onDelete = ForeignKey.CASCADE),
    ])
data class WorkoutHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val firebaseId: String = "",
    val userId: String,
    val exerciseId: Int,
    val programId: Int? = null,
    val remoteProgramId: String? = null,
    val date: Long,
    val setsCompleted: Int,
    val repsCompleted: Int,
    val weightUsed: Float? = null
): Parcelable
