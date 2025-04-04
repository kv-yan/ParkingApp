package am.android.parking.settings.domain.usecase

import am.android.parking.settings.data.datastore.LanguagePreferenceDataStore
import am.android.parking.settings.domain.model.AppLanguages

class SaveLanguageUseCase (
    private val languagePreferenceDataStore: LanguagePreferenceDataStore
) {
    suspend operator fun invoke(language: AppLanguages) {
        languagePreferenceDataStore.saveLanguage(language)
    }
}
