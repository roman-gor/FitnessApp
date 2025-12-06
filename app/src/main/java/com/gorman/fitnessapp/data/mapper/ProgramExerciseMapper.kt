package com.gorman.fitnessapp.data.mapper

import com.gorman.fitnessapp.data.models.postgresql.ProgramExerciseSupabase
import com.gorman.fitnessapp.data.models.postgresql.ProgramSupabase
import com.gorman.fitnessapp.data.models.room.ProgramEntity
import com.gorman.fitnessapp.data.models.room.ProgramExerciseEntity
import com.gorman.fitnessapp.domain.models.Program
import com.gorman.fitnessapp.domain.models.ProgramExercise

//ProgramExercise's mappers
fun ProgramExerciseEntity.toDomain(): ProgramExercise =
    ProgramExercise(
        id = id,
        supabaseId = supabaseId,
        exerciseId = exerciseId,
        order = order,
        dayOfWeek = dayOfWeek,
        sets = sets,
        repetitions = repetitions,
        durationSec = durationSec,
        caloriesBurned = caloriesBurned
    )

fun ProgramExerciseSupabase.toDomain(): ProgramExercise =
    ProgramExercise(
        supabaseId = id!!,
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
        supabaseId = supabaseId,
        programId = programId,
        exerciseId = exerciseId,
        order = order,
        dayOfWeek = dayOfWeek,
        sets = sets,
        repetitions = repetitions,
        durationSec = durationSec,
        caloriesBurned = caloriesBurned
    )

fun ProgramExercise.toRemote(programId: Int): ProgramExerciseSupabase =
    ProgramExerciseSupabase(
        programId = programId,
        exerciseId = exerciseId,
        order = order,
        dayOfWeek = dayOfWeek,
        sets = sets,
        repetitions = repetitions,
        durationSec = durationSec,
        caloriesBurned = caloriesBurned!!
    )

fun ProgramExerciseEntity.toRemote(): ProgramExerciseSupabase =
    ProgramExerciseSupabase(
        exerciseId = exerciseId,
        order = order,
        dayOfWeek = dayOfWeek,
        durationSec = durationSec,
        repetitions = repetitions,
        sets = sets,
        caloriesBurned = caloriesBurned!!
    )

//Program's mappers
fun ProgramEntity.toDomain(): Program = Program(
    localId = id,
    supabaseId = supabaseId,
    name = name,
    description = description,
    muscleGroup = muscleGroup,
    goalType = goalType
)
fun Program.toEntity(): ProgramEntity =
    ProgramEntity(
        id = localId,
        supabaseId = supabaseId,
        name = name,
        muscleGroup = muscleGroup,
        description = description,
        goalType = goalType
    )
fun Program.toRemote(): ProgramSupabase =
    ProgramSupabase(
        name = name,
        muscleGroup = muscleGroup,
        description = description,
        goalType = goalType
    )

fun ProgramSupabase.toDomain(): Program =
    Program(
        supabaseId = id!!,
        name = name,
        muscleGroup = muscleGroup,
        description = description,
        goalType = goalType
    )