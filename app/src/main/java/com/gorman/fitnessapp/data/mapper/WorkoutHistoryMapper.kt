package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.models.postgresql.WorkoutHistorySupabase
import com.gorman.fitnessapp.data.models.room.WorkoutHistoryEntity
import com.gorman.fitnessapp.domain.models.WorkoutHistory

fun WorkoutHistory.toRemote(): WorkoutHistorySupabase = WorkoutHistorySupabase(
    userId = remoteUserId,
    exerciseId = exerciseId,
    programId = remoteProgramId,
    date = date,
    setsCompleted = setsCompleted,
    repsCompleted = repsCompleted,
    weightUsed = weightUsed
)

fun WorkoutHistorySupabase.toDomain(): WorkoutHistory = WorkoutHistory(
    supabaseId = id,
    remoteUserId = userId,
    exerciseId = exerciseId,
    remoteProgramId = programId,
    date = date,
    setsCompleted = setsCompleted,
    repsCompleted = repsCompleted,
    weightUsed = weightUsed
)

fun WorkoutHistory.toEntity(): WorkoutHistoryEntity = WorkoutHistoryEntity(
    id = localId,
    supabaseId = supabaseId,
    remoteUserId = remoteUserId,
    exerciseId = exerciseId,
    programId = programId,
    remoteProgramId = remoteProgramId,
    date = date,
    setsCompleted = setsCompleted,
    repsCompleted = repsCompleted,
    weightUsed = weightUsed
)

fun WorkoutHistoryEntity.toDomain(): WorkoutHistory = WorkoutHistory(
    localId = id,
    supabaseId = supabaseId,
    remoteUserId = remoteUserId,
    exerciseId = exerciseId,
    programId = programId,
    remoteProgramId = remoteProgramId,
    date = date,
    setsCompleted = setsCompleted,
    repsCompleted = repsCompleted,
    weightUsed = weightUsed
)