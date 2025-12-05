package com.gorman.fitnessapp.domain.models

import kotlinx.serialization.Serializable

data class ProgramOutput(
    val template: Program,
    val userProgram: UserProgram
)
