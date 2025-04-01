package am.android.parking.analytics.domain.repository

import am.android.parking.analytics.domain.model.AnalyticsType
import am.android.parking.analytics.domain.model.ParkingAnalytics
import kotlinx.coroutines.flow.Flow

interface AnalyticsRepository {
    fun getAnalytics(type: AnalyticsType): Flow<ParkingAnalytics>
}
