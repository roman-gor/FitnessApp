package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.models.ProgramEntity
import com.gorman.fitnessapp.data.models.ProgramExerciseEntity
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.ProgramExercise
import com.gorman.fitnessapp.domain.models.ProgramTemplate

fun ProgramExerciseEntity.toDomain(): ProgramExercise =
    ProgramExercise(
        id = id,
        exerciseId = exerciseId,
        order = order,
        dayOfWeek = dayOfWeek,
        sets = sets,
        repetitions = repetitions,
        durationSec = durationSec,
        caloriesBurned = caloriesBurned
    )

fun ProgramExercise.toEntity(programId: Int): ProgramExerciseEntity =
    ProgramExerciseEntity(
        programId = programId,
        exerciseId = exerciseId,
        order = order,
        dayOfWeek = dayOfWeek,
        sets = sets,
        repetitions = repetitions,
        durationSec = durationSec,
        caloriesBurned = caloriesBurned
    )

fun Program.toEntity(): ProgramEntity =
    ProgramEntity(
        name = name,
        muscleGroup = muscleGroup,
        description = description,
        goalType = goalType
    )