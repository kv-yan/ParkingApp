package am.android.parking.settings.data.datastore

import am.android.parking.settings.domain.model.AppLanguages
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class LanguagePreferenceDataStore(
    private val context: Context,
) {
    companion object {
        val LANGUAGE_KEY = stringPreferencesKey("language_key")
    }

    val Context.languageDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

    suspend fun saveLanguage(language: AppLanguages) {
        context.languageDataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language.name
        }
    }

    val selectedLanguage: Flow<AppLanguages> = context.languageDataStore.data
        .catch { emit(emptyPreferences()) }
        .map { preferences ->
            val langName = preferences[LANGUAGE_KEY] ?: AppLanguages.Eng.name
            AppLanguages.valueOf(langName)
        }
}