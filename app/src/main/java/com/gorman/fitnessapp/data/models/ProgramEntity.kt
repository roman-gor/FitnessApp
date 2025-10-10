package com.gorman.fitnessapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Program")
data class ProgramEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String? = null,
    val exQuantity: Int? = null,
    val muscleGroup: String? = null,
    val complexity: Int? = null,
    val description: String? = null,
    val goalType: String? = null,
    val imageUrl: String? = null,
    val isCompleted: Boolean? = null
): Parcelable
