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
        firebaseId = firebaseId,
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
        id = id,
        firebaseId = firebaseId,
        programId = programId,
        exerciseId = exerciseId,
        order = order,
        dayOfWeek = dayOfWeek,
        sets = sets,
        repetitions = repetitions,
        durationSec = durationSec,
        caloriesBurned = caloriesBurned
    )

fun ProgramExercise.toRemote(): ProgramExerciseFirebase =
    ProgramExerciseFirebase(
        id = firebaseId,
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
        exerciseId = exerciseId,
        order = order,
        dayOfWeek = dayOfWeek,
        durationSec = durationSec,
        repetitions = repetitions,
        sets = sets,
        caloriesBurned = caloriesBurned
    )

//Program's mappers
fun ProgramEntity.toDomain(): Program = Program(
    localId = this.id,
    firebaseId = this.firebaseId,
    name = this.name,
    description = this.description,
    muscleGroup = this.muscleGroup,
    goalType = this.goalType
)
fun Program.toEntity(): ProgramEntity =
    ProgramEntity(
        id = localId,
        firebaseId = firebaseId,
        name = name,
        muscleGroup = muscleGroup,
        description = description,
        goalType = goalType
    )
fun Program.toRemote(): ProgramFirebase =
    ProgramFirebase(
        id = firebaseId,
        name = name,
        muscleGroup = muscleGroup,
        description = description,
        goalType = goalType
    )

fun ProgramFirebase.toDomain(): Program =
    Program(
        firebaseId = id,
        name = name,
        muscleGroup = muscleGroup,
        description = description,
        goalType = goalType
    )