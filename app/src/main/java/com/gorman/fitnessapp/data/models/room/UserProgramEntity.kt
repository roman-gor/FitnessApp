package com.gorman.fitnessapp.data.models.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "UserProgram",
    primaryKeys = ["programId"],
    indices = [
        Index(value = ["programId"])
    ],
    foreignKeys = [
        ForeignKey(entity = ProgramEntity::class, parentColumns = ["id"], childColumns = ["programId"], onDelete = ForeignKey.CASCADE)
    ])
data class UserProgramEntity(
    val supabaseId: Int = 0,
    val programId: Int,
    val startDate: Long,
    val endDate: Long? = null,
    val progress: Float? = null,
    val isCompleted: Boolean = false
): Parcelable