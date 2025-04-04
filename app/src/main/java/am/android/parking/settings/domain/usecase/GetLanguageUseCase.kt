package am.android.parking.settings.domain.usecase

import am.android.parking.settings.data.datastore.LanguagePreferenceDataStore
import am.android.parking.settings.domain.model.AppLanguages
import kotlinx.coroutines.flow.Flow

class GetLanguageUseCase(
    private val languagePreferenceDataStore: LanguagePreferenceDataStore
) {
    operator fun invoke(): Flow<AppLanguages> {
        return languagePreferenceDataStore.selectedLanguage
    }
}
