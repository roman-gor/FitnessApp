package com.gorman.fitnessapp.data.models.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "UsersData")
data class UsersDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String? = null,
    val email: String? = null,
    val birthday: Long? = null,
    val goal: String? = null,
    val weight: Float? = null,
    val desiredWeight: Float? = null,
    val height: Float? = null,
    val gender: String? = null,
    val photoUrl: String? = null,
    val activityLevel: String? = null,
    val experienceLevel: String? = null
): Parcelable

