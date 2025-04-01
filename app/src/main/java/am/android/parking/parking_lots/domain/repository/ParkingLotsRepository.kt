package am.android.parking.parking_lots.domain.repository

import am.android.parking.parking_lots.domain.model.ParkingLotStatus
import kotlinx.coroutines.flow.Flow

interface ParkingLotsRepository {
    fun getParkingLots(): Flow<ParkingLotStatus>
}