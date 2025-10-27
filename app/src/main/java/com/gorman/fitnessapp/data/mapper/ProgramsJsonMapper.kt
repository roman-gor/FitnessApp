package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.datasource.ai.dto.ProgramDto
import com.gorman.fitnessapp.data.datasource.ai.dto.ProgramExerciseDto
import com.gorman.fitnessapp.data.models.room.ProgramEntity
import com.gorman.fitnessapp.data.models.room.ProgramExerciseEntity
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.ProgramExercise

fun ProgramDto.toEntity(): ProgramEntity =
    ProgramEntity(
        name = name,
        description = description,
        muscleGroup = muscleGroup,
        goalType = goalType
    )

fun ProgramDto.toDomain(): Program =
    Program(
        name = name,
        description = description,
        muscleGroup = muscleGroup,
        goalType = goalType,
        exercises = exercises.map { it.toDomain() }
    )

fun ProgramExerciseDto.toEntity(programId: Int): ProgramExerciseEntity =
    ProgramExerciseEntity(
        programId = programId,
        exerciseId = exerciseId,
        order = order,
        dayOfWeek = dayOfWeek,
        durationSec = durationSec,
        sets = sets,
        repetitions = repetitions,
        caloriesBurned = caloriesBurned.toFloat()
    )

fun ProgramExerciseDto.toDomain(): ProgramExercise =
    ProgramExercise(
        exerciseId = exerciseId,
        order = order,
        dayOfWeek = dayOfWeek,
        durationSec = durationSec,
        sets = sets,
        repetitions = repetitions,
        caloriesBurned = caloriesBurned.toFloat()
    )