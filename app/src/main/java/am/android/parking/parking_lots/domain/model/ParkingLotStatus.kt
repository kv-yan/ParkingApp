package am.android.parking.parking_lots.domain.model

data class ParkingLotStatus(
    val totalSpots: Int,
    val availableSpots: Int,
    val occupiedSpots: Int,
    val spots: List<ParkingSpot>,
)