package am.android.parking.diagrams.domain.model

data class ParkingOccupancyData(
    val month: String,
    val occupancyPercentage: Float,
    val baseRate: Float = 10f, // Base rate per parking spot
    val totalSpots: Int = 1000 // Total parking spots available
) {
    // Calculated properties
    val occupiedSpots: Int
        get() = (totalSpots * occupancyPercentage).toInt()

    val monthlyRevenue: Float
        get() = occupiedSpots * baseRate * 30f // Assuming 30 days in month

    val formattedRevenue: String
        get() = "$${"%,.0f".format(monthlyRevenue)}"
}

// Updated mock data with realistic revenue values
val mockParkingData = listOf(
    ParkingOccupancyData("Jun", 0.85f, baseRate = 12f),
    ParkingOccupancyData("Jul", 0.78f, baseRate = 12f),
    ParkingOccupancyData("Aug", 0.92f, baseRate = 15f), // Higher rate in peak season
    ParkingOccupancyData("Sep", 0.88f, baseRate = 15f),
    ParkingOccupancyData("Oct", 0.75f, baseRate = 12f),
    ParkingOccupancyData("Nov", 0.82f, baseRate = 12f),
    ParkingOccupancyData("Dec", 0.95f, baseRate = 18f), // Holiday season premium
    ParkingOccupancyData("Jan", 0.72f, baseRate = 10f),
    ParkingOccupancyData("Feb", 0.68f, baseRate = 10f),
    ParkingOccupancyData("Mar", 0.79f, baseRate = 12f),
    ParkingOccupancyData("Apr", 0.83f, baseRate = 12f),
    ParkingOccupancyData("May", 0.91f, baseRate = 15f)
)

