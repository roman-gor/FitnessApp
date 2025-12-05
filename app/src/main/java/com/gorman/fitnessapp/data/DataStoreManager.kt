package com.gorman.fitnessapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object SettingsKeys {
    val USER_ID = intPreferencesKey("user_id")
    val PROGRAM_ID = intPreferencesKey("program_id")
    val MEAL_ID = intPreferencesKey("meal_id")
}
