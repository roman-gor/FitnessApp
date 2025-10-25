package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.models.firebase.UserProgramFirebase
import com.gorman.fitnessapp.data.models.room.UserProgramEntity
import com.gorman.fitnessapp.domain.models.UserProgram

fun UserProgramEntity.toDomain(): UserProgram =
    UserProgram(
        programId = programId,
        startDate = startDate,
        endDate = endDate,
        progress = progress,
        isCompleted = isCompleted
    )

fun UserProgram.toEntity(): UserProgramEntity =
    UserProgramEntity(
        programId = programId,
        startDate = startDate,
        endDate = endDate,
        progress = progress,
        isCompleted = isCompleted
    )

fun UserProgram.toRemote(): UserProgramFirebase =
    UserProgramFirebase(
        userId = userId,
        programId = firebaseId,
        startDate = startDate,
        endDate = endDate,
        progress = progress,
        isCompleted = isCompleted
    )

fun UserProgramFirebase.toDomain(): UserProgram =
    UserProgram(
        userId = userId,
        firebaseId = programId,
        startDate = startDate,
        endDate = endDate,
        progress = progress,
        isCompleted = isCompleted
    )