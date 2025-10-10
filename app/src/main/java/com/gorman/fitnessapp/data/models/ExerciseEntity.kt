package com.gorman.fitnessapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Exercise")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String? = null,
    val description: String? = null,
    val muscleGroup: String? = null,
    val complexity: Int? = null,
    val videoUrl: String? = null,
    val imageUrl: String? = null,
    val durationSec: Int? = null,
    val repetitions: Int? = null,
    val sets: Int? = null,
    val caloriesBurned: Float? = null
): Parcelable
