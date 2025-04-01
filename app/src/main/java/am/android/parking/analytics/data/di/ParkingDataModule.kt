package am.android.parking.analytics.data.di

import am.android.parking.analytics.data.repository.MockAnalyticsRepositoryImpl
import am.android.parking.analytics.domain.repository.AnalyticsRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val parkingDataModule = module {
    singleOf(::MockAnalyticsRepositoryImpl) { bind<AnalyticsRepository>() }
}