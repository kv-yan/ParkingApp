package am.android.parking.diagrams.domain.model

data class ParkingOccupancyData(
    val month: String,
    val occupancyPercentage: Float
)

val mockParkingData = listOf(
    ParkingOccupancyData("Jun", 0.85f),
    ParkingOccupancyData("Jul", 0.78f),
    ParkingOccupancyData("Aug", 0.92f),
    ParkingOccupancyData("Sep", 0.88f),
    ParkingOccupancyData("Oct", 0.75f),
    ParkingOccupancyData("Nov", 0.82f),
    ParkingOccupancyData("Dec", 0.95f),
    ParkingOccupancyData("Jan", 0.72f),
    ParkingOccupancyData("Feb", 0.68f),
    ParkingOccupancyData("Mar", 0.79f),
    ParkingOccupancyData("Apr", 0.83f),
    ParkingOccupancyData("May", 0.91f)
)