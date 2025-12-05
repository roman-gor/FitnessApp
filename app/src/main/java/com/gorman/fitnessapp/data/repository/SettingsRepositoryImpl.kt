package com.gorman.fitnessapp.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.gorman.fitnessapp.data.SettingsKeys
import com.gorman.fitnessapp.data.dataStore
import com.gorman.fitnessapp.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(private val context: Context): SettingsRepository {
    override val userIdFlow: Flow<Int> = context.dataStore.data
        .map { prefs -> prefs[SettingsKeys.USER_ID] ?: 0 }
    override val programIdFlow: Flow<Int> = context.dataStore.data
        .map { prefs -> prefs[SettingsKeys.PROGRAM_ID] ?: 0 }
    override val mealIdFlow: Flow<Int> = context.dataStore.data
        .map { prefs -> prefs[SettingsKeys.MEAL_ID] ?: 0 }

    override suspend fun setUserId(id: Int) {
        context.dataStore.edit { prefs ->
            prefs[SettingsKeys.USER_ID] = id
        }
    }

    override suspend fun setProgramId(id: Int) {
        context.dataStore.edit { prefs ->
            prefs[SettingsKeys.PROGRAM_ID] = id
        }
    }

    override suspend fun setMealId(id: Int) {
        context.dataStore.edit { prefs ->
            prefs[SettingsKeys.MEAL_ID] = id
        }
    }
}