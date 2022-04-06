package com.herdi.yusli.githubappherdiyusli.fitur.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesThemeSettings private constructor(private val dataStore: DataStore<Preferences>) {

    private val KEY_THEME = booleanPreferencesKey("THEME_SETTINGS")


    companion object {
        @Volatile
        private var INSTANCE: PreferencesThemeSettings? = null

        fun getInstancePref(dataStore: DataStore<Preferences>): PreferencesThemeSettings {
            return INSTANCE ?: synchronized(this) {
                val instance = PreferencesThemeSettings(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    fun getThemeSettings(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[KEY_THEME] ?: false
        }
    }

    suspend fun saveThemeSettings(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_THEME] = isDarkModeActive
        }
    }
}