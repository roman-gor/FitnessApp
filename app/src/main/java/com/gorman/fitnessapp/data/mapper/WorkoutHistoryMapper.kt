package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.models.firebase.WorkoutHistoryFirebase
import com.gorman.fitnessapp.data.models.room.WorkoutHistoryEntity
import com.gorman.fitnessapp.domain.models.WorkoutHistory

fun WorkoutHistory.toRemote(): WorkoutHistoryFirebase = WorkoutHistoryFirebase(
    userId = firebaseUserId,
    exerciseId = exerciseId,
    programId = remoteProgramId,
    date = date,
    setsCompleted = setsCompleted,
    repsCompleted = repsCompleted,
    weightUsed = weightUsed
)

fun WorkoutHistoryFirebase.toDomain(): WorkoutHistory = WorkoutHistory(
    firebaseId = id,
    firebaseUserId = userId,
    exerciseId = exerciseId,
    remoteProgramId = programId,
    date = date,
    setsCompleted = setsCompleted,
    repsCompleted = repsCompleted,
    weightUsed = weightUsed
)

fun WorkoutHistory.toEntity(): WorkoutHistoryEntity = WorkoutHistoryEntity(
    id = localId,
    firebaseId = firebaseId,
    userId = firebaseUserId,
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
    firebaseId = firebaseId,
    firebaseUserId = userId,
    exerciseId = exerciseId,
    programId = programId,
    remoteProgramId = remoteProgramId,
    date = date,
    setsCompleted = setsCompleted,
    repsCompleted = repsCompleted,
    weightUsed = weightUsed
)