package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.models.postgresql.UserProgramSupabase
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

fun UserProgram.toRemote(): UserProgramSupabase =
    UserProgramSupabase(
        userId = userId,
        programId = programId,
        startDate = startDate,
        endDate = endDate,
        progress = progress,
        isCompleted = isCompleted
    )

fun UserProgramSupabase.toDomain(): UserProgram =
    UserProgram(
        userId = userId,
        supabaseId = programId,
        startDate = startDate,
        endDate = endDate,
        progress = progress,
        isCompleted = isCompleted
    )