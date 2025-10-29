package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.models.postgresql.UserProgressSupabase
import com.gorman.fitnessapp.data.models.room.UserProgressEntity
import com.gorman.fitnessapp.domain.models.UserProgress

fun UserProgressSupabase.toDomain(): UserProgress = UserProgress(
    supabaseId = id,
    remoteUserId = userId,
    date = date,
    weight = weight,
    caloriesBurned = caloriesBurned,
    durationMinutes = durationMinutes,
    notes = notes
)

fun UserProgress.toRemote(): UserProgressSupabase = UserProgressSupabase(
    id = supabaseId,
    userId = remoteUserId,
    date = date,
    weight = weight,
    caloriesBurned = caloriesBurned,
    durationMinutes = durationMinutes,
    notes = notes
)

fun UserProgress.toEntity(): UserProgressEntity = UserProgressEntity(
    supabaseId = supabaseId,
    remoteUserId = remoteUserId,
    date = date,
    weight = weight,
    caloriesBurned = caloriesBurned,
    durationMinutes = durationMinutes,
    notes = notes
)

fun UserProgressEntity.toDomain(): UserProgress = UserProgress(
    localId = id,
    supabaseId = supabaseId,
    remoteUserId = remoteUserId,
    date = date,
    weight = weight,
    caloriesBurned = caloriesBurned,
    durationMinutes = durationMinutes,
    notes = notes
)