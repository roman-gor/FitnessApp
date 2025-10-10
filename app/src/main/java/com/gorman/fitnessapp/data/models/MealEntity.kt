package com.gorman.fitnessapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Meal",
    indices = [
        Index(value = ["userId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = UsersDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val name: String,
    val calories: Float,
    val protein: Float,
    val carbs: Float,
    val fats: Float,
    val date: Long
): Parcelable
