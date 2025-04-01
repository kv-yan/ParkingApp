package am.android.parking.analytics.domain.model

data class ParkingSession(
    val spotId: String,
    val startTime: Long,
    val endTime: Long?,
    val feeCollected: Double
)
