package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.models.firebase.ExerciseFirebase
import com.gorman.fitnessapp.data.models.room.ExerciseEntity
import com.gorman.fitnessapp.domain.models.Exercise

fun ExerciseEntity.toDomain(): Exercise =
    Exercise(
        id = id,
        firebaseId = firebaseId,
        name = name,
        description = description,
        muscleGroup = muscleGroup,
        complexity = complexity,
        videoUrl = videoUrl,
        imageUrl = imageUrl)

fun ExerciseFirebase.toDomain(): Exercise =
    Exercise(
        name = name,
        firebaseId = id,
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