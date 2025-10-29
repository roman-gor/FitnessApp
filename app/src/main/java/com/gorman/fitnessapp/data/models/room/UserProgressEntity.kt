package com.gorman.fitnessapp.data.models.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "UserProgress")
data class UserProgressEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val supabaseId: Int = 0,
    val remoteUserId: Int,
    val date: Long,
    val weight: Float? = null,
    val caloriesBurned: Float? = null,
    val durationMinutes: Int? = null,
    val notes: String? = null
): Parcelable
