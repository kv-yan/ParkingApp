package am.android.parking.analytics.domain.usecase

import am.android.parking.analytics.domain.model.AnalyticsType
import am.android.parking.analytics.domain.model.ParkingAnalytics
import am.android.parking.analytics.domain.repository.AnalyticsRepository
import kotlinx.coroutines.flow.Flow

interface GetAnalyticsUseCase {
    operator fun invoke(type: AnalyticsType): Flow<ParkingAnalytics>

}

class GetAnalyticsUseCaseImpl(
    private val analyticsRepository: AnalyticsRepository,
) : GetAnalyticsUseCase {
    override fun invoke(type: AnalyticsType) = analyticsRepository.getAnalytics(type)
}