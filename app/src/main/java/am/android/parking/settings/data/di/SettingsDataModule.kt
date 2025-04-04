package am.android.parking.settings.data.di

import am.android.parking.settings.data.datastore.LanguagePreferenceDataStore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val settingsDataModule = module {
    singleOf(::LanguagePreferenceDataStore)
}