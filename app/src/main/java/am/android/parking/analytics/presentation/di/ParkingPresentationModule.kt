package am.android.parking.analytics.presentation.di

import am.android.parking.analytics.presentation.AnalyticsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val parkingPresentationModule = module {
    viewModelOf(::AnalyticsViewModel)
}