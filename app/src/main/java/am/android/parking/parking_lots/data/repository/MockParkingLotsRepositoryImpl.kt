package am.android.parking.parking_lots.data.repository

import am.android.parking.parking_lots.domain.model.ParkingLotStatus
import am.android.parking.parking_lots.domain.model.ParkingSpot
import am.android.parking.parking_lots.domain.repository.ParkingLotsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockParkingLotsRepositoryImpl : ParkingLotsRepository {

    private val totalSpots = 40

    override fun getParkingLots(): Flow<ParkingLotStatus> = flow {
        while (true) {
            emit(generateParkingLotStatus())
            delay(35000)
        }
    }

    private fun generateParkingLotStatus(): ParkingLotStatus {
        // Generate a list of parking spots with random availability
        val spots = (1..totalSpots).map {
            val isAvailable = (0..1).random() == 0 // Randomly set the status (50% chance)
            ParkingSpot(id = it, isAvailable = isAvailable)
        }

        val availableSpots = spots.count { it.isAvailable }
        val occupiedSpots = totalSpots - availableSpots

        return ParkingLotStatus(
            totalSpots = totalSpots,
            availableSpots = availableSpots,
            occupiedSpots = occupiedSpots,
            spots = spots
        )
    }
}
