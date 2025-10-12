package com.gorman.fitnessapp.data.models

import android.os.Parcelable
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import androidx.room.Entity
import androidx.room.Index
@Parcelize
@Entity(tableName = "MealPlanItem",
    indices = [
        Index(value = ["templateId"]),
        Index(value = ["mealId"]),
        Index(value = ["templateId", "date", "mealType"], unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = MealPlanTemplateEntity::class,
            parentColumns = ["id"],
            childColumns = ["templateId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MealEntity::class,
            parentColumns = ["id"],
            childColumns = ["mealId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class MealPlanItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val templateId: Int,
    val mealId: Int,
    val date: Long,
    val mealType: String,
    val quantity: Int = 1,
    val notes: String? = null
): Parcelable