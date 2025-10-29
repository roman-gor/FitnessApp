package com.gorman.fitnessapp.data.models.postgresql

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkoutHistorySupabase(
    @SerialName("id_workoutprogress")
    val id: Int = 0,
    @SerialName("userid_usersdata")
    val userId: Int,
    @SerialName("exerciseid_exercise")
    val exerciseId: Int,
    @SerialName("programid_program")
    val programId: Int? = null,
    @SerialName("date")
    val date: Long,
    @SerialName("setscompleted")
    val setsCompleted: Int,
    @SerialName("repscompleted")
    val repsCompleted: Int,
    @SerialName("weightused")
    val weightUsed: Float? = null
)
