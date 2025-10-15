package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.models.firebase.ProgramExerciseFirebase
import com.gorman.fitnessapp.data.models.firebase.ProgramFirebase
import com.gorman.fitnessapp.data.models.room.ProgramEntity
import com.gorman.fitnessapp.data.models.room.ProgramExerciseEntity
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.ProgramExercise

//ProgramExercise's mappers
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

fun ProgramExercise.toRemote(programId: Int): ProgramExerciseFirebase =
    ProgramExerciseFirebase(
        programId = programId,
        exerciseId = exerciseId,
        order = order,
        dayOfWeek = dayOfWeek,
        sets = sets,
        repetitions = repetitions,
        durationSec = durationSec,
        caloriesBurned = caloriesBurned
    )

fun ProgramExerciseEntity.toRemote(): ProgramExerciseFirebase =
    ProgramExerciseFirebase(
        id = id,
        programId = programId,
        exerciseId = exerciseId,
        order = order,
        dayOfWeek = dayOfWeek,
        durationSec = durationSec,
        repetitions = repetitions,
        sets = sets,
        caloriesBurned = caloriesBurned
    )

//Program's mappers
fun Program.toEntity(): ProgramEntity =
    ProgramEntity(
        name = name,
        muscleGroup = muscleGroup,
        description = description,
        goalType = goalType
    )

fun Program.toRemote(): ProgramFirebase =
    ProgramFirebase(
        id = programId.toInt(),
        name = name,
        muscleGroup = muscleGroup,
        description = description,
        goalType = goalType
    )

fun ProgramEntity.toRemote(): ProgramFirebase =
    ProgramFirebase(
        id = id,
        name = name,
        muscleGroup = muscleGroup,
        description = description,
        goalType = goalType
    )

fun ProgramFirebase.toEntity(): ProgramEntity =
    ProgramEntity(
        id = id,
        name = name,
        muscleGroup = muscleGroup,
        description = description,
        goalType = goalType
    )