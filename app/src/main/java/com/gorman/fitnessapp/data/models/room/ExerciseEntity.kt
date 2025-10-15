package com.gorman.fitnessapp.data.models.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Exercise")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val firebaseId: String = "",
    val name: String,
    val description: String? = null,
    val muscleGroup: String,
    val complexity: Int? = null,
    val videoUrl: String? = null,
    val imageUrl: String? = null
): Parcelable
