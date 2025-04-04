package am.android.parking

import am.android.parking.analytics.data.di.parkingDataModule
import am.android.parking.analytics.domain.di.parkingDomainModule
import am.android.parking.analytics.presentation.di.parkingPresentationModule
import am.android.parking.parking_lots.data.di.lotsDataModule
import am.android.parking.parking_lots.presentation.di.lotsPresentationModule
import am.android.parking.settings.data.di.settingsDataModule
import am.android.parking.settings.domain.di.settingsDomainModule
import am.android.parking.settings.domain.model.AppLanguages
import am.android.parking.settings.presentation.di.settingsPresentationModule
import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@Application)
            modules(
                parkingPresentationModule,
                parkingDomainModule,
                parkingDataModule,
                lotsPresentationModule,
                lotsDataModule,
                settingsPresentationModule,
                settingsDomainModule,
                settingsDataModule,
            )
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(updateLocale(base))
    }

    fun updateLocale(context: Context): Context {
        // We'll handle the actual locale update in activities
        // since we need to use DataStore asynchronously
        return context
    }
}