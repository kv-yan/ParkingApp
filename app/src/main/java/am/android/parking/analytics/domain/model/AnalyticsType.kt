package am.android.parking.analytics.domain.model

sealed class AnalyticsType {
    data class Daily(val date: String) : AnalyticsType()
    data class Weekly(val weekStart: String, val weekEnd: String) : AnalyticsType()
    data class Monthly(val month: String) : AnalyticsType()
}
