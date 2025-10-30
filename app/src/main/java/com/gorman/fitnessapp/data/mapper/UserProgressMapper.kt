package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.models.firebase.UserProgressFirebase
import com.gorman.fitnessapp.data.models.room.UserProgressEntity
import com.gorman.fitnessapp.domain.models.UserProgress

fun UserProgressFirebase.toDomain(): UserProgress = UserProgress(
    firebaseId = id,
    userId = userId,
    date = date,
    weight = weight,
    caloriesBurned = caloriesBurned,
    durationMinutes = durationMinutes,
    notes = notes
)

fun UserProgress.toEntity(): UserProgressEntity = UserProgressEntity(
    firebaseId = firebaseId,
    userId = userId,
    date = date,
    weight = weight,
    caloriesBurned = caloriesBurned,
    durationMinutes = durationMinutes,
    notes = notes
)

fun UserProgress.toRemote(): UserProgressFirebase = UserProgressFirebase(
    id = firebaseId,
    userId = userId,
    date = date,
    weight = weight,
    caloriesBurned = caloriesBurned,
    durationMinutes = durationMinutes,
    notes = notes
)

fun UserProgressEntity.toDomain(): UserProgress = UserProgress(
    localId = id,
    firebaseId = firebaseId,
    userId = userId,
    date = date,
    weight = weight,
    caloriesBurned = caloriesBurned,
    durationMinutes = durationMinutes,
    notes = notes
)