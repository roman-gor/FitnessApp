package com.gorman.fitnessapp.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.gorman.fitnessapp.data.SettingsKeys
import com.gorman.fitnessapp.data.dataStore
import com.gorman.fitnessapp.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(private val context: Context): SettingsRepository {
    override val userIdFlow: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[SettingsKeys.USER_ID] ?: "0" }

    override suspend fun setUserId(id: String) {
        context.dataStore.edit { prefs ->
            prefs[SettingsKeys.USER_ID] = id
        }
    }
}