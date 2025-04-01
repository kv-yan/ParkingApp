package am.android.parking.parking_lots.data.di

import am.android.parking.parking_lots.domain.repository.ParkingLotsRepository
import am.android.parking.parking_lots.data.repository.MockParkingLotsRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val lotsDataModule = module {
    singleOf(::MockParkingLotsRepositoryImpl) { bind<ParkingLotsRepository>() }
}
