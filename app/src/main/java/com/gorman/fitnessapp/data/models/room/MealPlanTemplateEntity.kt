package com.gorman.fitnessapp.data.models.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "MealPlanTemplate")
data class MealPlanTemplateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val supabaseId: Int = 0,
    val userSupabaseId: Int = 0,
    val name: String,
    val description: String? = null
): Parcelable