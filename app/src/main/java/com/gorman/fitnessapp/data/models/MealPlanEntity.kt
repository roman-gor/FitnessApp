package com.gorman.fitnessapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "MealPlan",
    indices = [
        Index(value = ["userId"]),
        Index(value = ["mealId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = UsersDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(entity = MealEntity::class, parentColumns = ["id"], childColumns = ["mealId"], onDelete = ForeignKey.CASCADE)
    ])
data class MealPlanEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val mealId: Int,
    val name: String,
    val date: Long,
    val mealType: String? = null,
    val notes: String? = null
): Parcelable
