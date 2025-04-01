package am.android.parking.analytics.domain.di

import am.android.parking.analytics.domain.usecase.GetAnalyticsUseCase
import am.android.parking.analytics.domain.usecase.GetAnalyticsUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module


val parkingDomainModule = module {
    factoryOf(::GetAnalyticsUseCaseImpl) { bind<GetAnalyticsUseCase>() }
}