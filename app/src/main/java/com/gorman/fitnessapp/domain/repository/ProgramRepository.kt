package com.gorman.fitnessapp.domain.repository

import com.gorman.fitnessapp.domain.models.UsersData

interface ProgramRepository {
    suspend fun generateAndSyncProgram(usersData: UsersData, selectedProgramIndex: Int): String
}