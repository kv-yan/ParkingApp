package am.android.parking.settings.domain.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import am.android.parking.settings.domain.usecase.GetLanguageUseCase
import am.android.parking.settings.domain.usecase.SaveLanguageUseCase

val settingsDomainModule = module {
    factoryOf(::SaveLanguageUseCase)
    factoryOf(::GetLanguageUseCase)

}