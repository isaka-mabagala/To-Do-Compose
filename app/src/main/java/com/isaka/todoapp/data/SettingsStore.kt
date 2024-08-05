package com.isaka.todoapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.isaka.todoapp.data.SettingsStore.Companion
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Companion.PREFERENCE_NAME)

@ViewModelScoped
class SettingsStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object PreferenceKeys {
        val isDarkMode = booleanPreferencesKey("is_dark_mode")
    }

    private val dataStore = context.dataStore

    val isDarkModeTheme: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preference ->
            val isDarkMode = preference[PreferenceKeys.isDarkMode] ?: false
            isDarkMode
        }

    suspend fun setDarkMode(status: Boolean) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.isDarkMode] = status
        }
    }

    companion object {
        const val PREFERENCE_NAME = "settings"
    }
}
