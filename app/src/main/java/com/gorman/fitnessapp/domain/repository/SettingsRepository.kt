package com.gorman.fitnessapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val userIdFlow: Flow<String>
    val programIdFlow: Flow<String>
    suspend fun setUserId(id: String)
    suspend fun setProgramId(id: String)
}