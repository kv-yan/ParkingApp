package am.android.parking

import am.android.parking.analytics.data.di.parkingDataModule
import am.android.parking.analytics.domain.di.parkingDomainModule
import am.android.parking.analytics.presentation.di.parkingPresentationModule
import am.android.parking.parking_lots.data.di.lotsDataModule
import am.android.parking.parking_lots.presentation.di.lotsPresentationModule
import android.app.Application
import org.koin.core.context.GlobalContext.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                parkingPresentationModule,
                parkingDomainModule,
                parkingDataModule,
                lotsPresentationModule,
                lotsDataModule
            )
        }
    }
}