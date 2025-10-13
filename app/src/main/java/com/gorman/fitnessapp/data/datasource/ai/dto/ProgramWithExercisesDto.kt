package com.gorman.fitnessapp.data.datasource.ai.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProgramWithExercisesDto(
    val program: ProgramDto,
    val exercises: List<ProgramExerciseDto>
)
