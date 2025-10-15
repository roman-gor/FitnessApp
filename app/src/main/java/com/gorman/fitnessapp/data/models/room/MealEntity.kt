package com.gorman.fitnessapp.data.models.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Meal")
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val calories: Float,
    val protein: Float,
    val carbs: Float,
    val fats: Float,
    val recipe: String,
): Parcelable
