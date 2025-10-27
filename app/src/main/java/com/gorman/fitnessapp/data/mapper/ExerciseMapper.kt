package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.models.postgresql.ExerciseSupabase
import com.gorman.fitnessapp.data.models.room.ExerciseEntity
import com.gorman.fitnessapp.domain.models.Exercise

fun ExerciseEntity.toDomain(): Exercise =
    Exercise(
        id = id,
        supabaseId = supabaseId,
        name = name,
        description = description,
        muscleGroup = muscleGroup,
        complexity = complexity,
        videoUrl = videoUrl,
        imageUrl = imageUrl)

fun ExerciseSupabase.toDomain(): Exercise =
    Exercise(
        name = name,
        supabaseId = id,
        description = description,
        muscleGroup = muscleGroup,
        complexity = complexity,
        videoUrl = videoUrl,
        imageUrl = imageUrl)

fun Exercise.toEntity(): ExerciseEntity =
    ExerciseEntity(
        id = id,
        supabaseId = supabaseId,
        name = name,
        description = description,
        muscleGroup = muscleGroup,
        complexity = complexity,
        videoUrl = videoUrl,
        imageUrl = imageUrl)