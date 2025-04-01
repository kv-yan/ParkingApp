package am.android.parking.analytics.domain.model

data class ParkingAnalytics(
    val totalRevenue: Double,
    val averageParkingTime: Long,
    val carsParked: Int
)
