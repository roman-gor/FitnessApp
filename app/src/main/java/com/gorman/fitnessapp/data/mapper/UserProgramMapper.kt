package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.models.UserProgramEntity
import com.gorman.fitnessapp.domain.models.UserProgram

fun UserProgramEntity.toDomain(): UserProgram =
    UserProgram(
        userId = userId,
        programId = programId,
        startDate = startDate,
        endDate = endDate,
        progress = progress,
        isCompleted = isCompleted
    )

fun UserProgram.toEntity(): UserProgramEntity =
    UserProgramEntity(
        userId = userId,
        programId = programId,
        startDate = startDate,
        endDate = endDate,
        progress = progress,
        isCompleted = isCompleted
    )