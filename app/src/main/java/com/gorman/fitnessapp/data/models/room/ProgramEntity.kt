package com.gorman.fitnessapp.data.models.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Program")
data class ProgramEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val muscleGroup: String,
    val description: String? = null,
    val goalType: String? = null
): Parcelable
