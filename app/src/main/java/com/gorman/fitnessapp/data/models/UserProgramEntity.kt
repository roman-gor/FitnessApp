package com.gorman.fitnessapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "UserProgram",
    primaryKeys = ["userId", "programId"],
    indices = [
        Index(value = ["programId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = UsersDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(entity = ProgramEntity::class, parentColumns = ["id"], childColumns = ["programId"], onDelete = ForeignKey.CASCADE)
    ])
data class UserProgramEntity(
    val userId: Int,
    val programId: Int,
    val startDate: Long,
    val endDate: Long? = null,
    val progress: Float? = null,
    val isCompleted: Boolean = false
): Parcelable
