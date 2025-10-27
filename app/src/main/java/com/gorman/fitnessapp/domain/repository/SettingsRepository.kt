package com.gorman.fitnessapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val userIdFlow: Flow<Int>
    suspend fun setUserId(id: Int)
}