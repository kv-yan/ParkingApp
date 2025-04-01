package am.android.parking.parking_lots.presentation.di

import am.android.parking.parking_lots.presentation.ParkingLotViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val lotsPresentationModule = module {
    viewModelOf(::ParkingLotViewModel)
}
