package am.android.parking.analytics.data.repository

import am.android.parking.analytics.domain.model.AnalyticsType
import am.android.parking.analytics.domain.model.ParkingAnalytics
import am.android.parking.analytics.domain.model.ParkingSession
import am.android.parking.analytics.domain.repository.AnalyticsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import kotlin.random.Random


class MockAnalyticsRepositoryImpl : AnalyticsRepository {

    private val mockSessions: List<ParkingSession> = generateMockParkingSessions(60, 50) // 60 days

    override fun getAnalytics(type: AnalyticsType): Flow<ParkingAnalytics> = flow {
        emit(
            when (type) {
                is AnalyticsType.Daily -> calculateAnalytics(type.date)
                is AnalyticsType.Weekly -> calculateWeeklyAnalytics(type.weekStart, type.weekEnd)
                is AnalyticsType.Monthly -> calculateMonthlyAnalytics(type.month)
            }
        )
    }.flowOn(Dispatchers.IO)

    private fun generateMockParkingSessions(days: Int, spots: Int): List<ParkingSession> {
        val sessions = mutableListOf<ParkingSession>()
        val now = System.currentTimeMillis()
        val dayMillis = 24 * 60 * 60 * 1000

        repeat(days) { dayOffset ->
            val dayStart = now - (dayOffset * dayMillis)

            repeat(Random.nextInt(spots / 2, spots)) {
                val startTime = dayStart + Random.nextLong(0, dayMillis.toLong())
                val duration = Random.nextLong(30 * 60 * 1000, 6 * 60 * 60 * 1000)
                val endTime = startTime + duration
                val fee = (duration / (60 * 60 * 1000)) * 2.5

                sessions.add(ParkingSession(UUID.randomUUID().toString(), startTime, endTime, fee))
            }
        }

        return sessions
    }

    private fun calculateAnalytics(date: String): ParkingAnalytics {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val filteredSessions = mockSessions.filter { dateFormat.format(Date(it.startTime)) == date }

        return aggregateData(filteredSessions)
    }

    private fun calculateWeeklyAnalytics(start: String, end: String): ParkingAnalytics {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val startDate = dateFormat.parse(start)!!.time
        val endDate = dateFormat.parse(end)!!.time

        val filteredSessions = mockSessions.filter { it.startTime in startDate..endDate }
        return aggregateData(filteredSessions)
    }

    private fun calculateMonthlyAnalytics(month: String): ParkingAnalytics {
        val monthFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        val filteredSessions =
            mockSessions.filter { monthFormat.format(Date(it.startTime)) == month }

        return aggregateData(filteredSessions)
    }

    private fun aggregateData(sessions: List<ParkingSession>): ParkingAnalytics {
        return ParkingAnalytics(
            totalRevenue = sessions.sumOf { it.feeCollected },
            averageParkingTime = if (sessions.isNotEmpty()) sessions.map {
                (it.endTime ?: it.startTime) - it.startTime
            }.average().toLong() else 0L,
            carsParked = sessions.size
        )
    }
}
