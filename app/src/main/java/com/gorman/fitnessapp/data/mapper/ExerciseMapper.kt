package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.models.ExerciseEntity
import com.gorman.fitnessapp.domain.models.Exercise

fun ExerciseEntity.toDomain(): Exercise =
    Exercise(
        id = id,
        name = name,
        description = description,
        muscleGroup = muscleGroup,
        complexity = complexity,
        videoUrl = videoUrl,
        imageUrl = imageUrl)

fun Exercise.toEntity(): ExerciseEntity =
    ExerciseEntity(
        id = id,
        name = name,
        description = description,
        muscleGroup = muscleGroup,
        complexity = complexity,
        videoUrl = videoUrl,
        imageUrl = imageUrl)