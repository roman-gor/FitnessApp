package com.gorman.fitnessapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val userIdFlow: Flow<Int>
    val programIdFlow: Flow<Int>
    suspend fun setUserId(id: Int)
    suspend fun setProgramId(id: Int)
}