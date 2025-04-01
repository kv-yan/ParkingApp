package am.android.parking.analytics.presentation

import am.android.parking.analytics.domain.model.AnalyticsType
import am.android.parking.analytics.domain.model.ParkingAnalytics
import am.android.parking.analytics.domain.usecase.GetAnalyticsUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AnalyticsViewModel(
    private val getAnalyticsUseCase: GetAnalyticsUseCase,
) : ViewModel() {

    private val _analytics = MutableStateFlow<ParkingAnalytics?>(null)
    val analytics = _analytics.asStateFlow()

    private val _analyticsShowingType = MutableStateFlow(getCurrentDayAnalyticsType())
    val analyticsShowingType = _analyticsShowingType.asStateFlow()

    val _timePeriods = MutableStateFlow(getTimePeriods())
    val timePeriods = _timePeriods.asStateFlow()


    init {
        fetchAnalytics()
    }

    private fun fetchAnalytics() {
        viewModelScope.launch {
            getAnalyticsUseCase(_analyticsShowingType.value).collect { analyticsData ->
                _analytics.value = analyticsData
            }
        }
    }

    fun setShowingType(type: AnalyticsType) {
        _analyticsShowingType.value = type
        fetchAnalytics()
    }

    private fun getCurrentDayAnalyticsType(): AnalyticsType {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        return AnalyticsType.Daily(currentDate)
    }

    private fun getTimePeriods(): List<Pair<String, AnalyticsType>> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val monthFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())

        val calendar = Calendar.getInstance()

        // Today
        val todayDate = dateFormat.format(calendar.time)
        val today = "Today" to AnalyticsType.Daily(todayDate)

        // Last 3 days
        val lastDays = (1..3).map {
            calendar.add(Calendar.DAY_OF_MONTH, -1)
            val date = dateFormat.format(calendar.time)
            val label = when (it) {
                1 -> "Yesterday"
                2 -> "2 Days Ago"
                3 -> "3 Days Ago"
                else -> date
            }
            label to AnalyticsType.Daily(date)
        }

        // Last week (Monday-Sunday)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        val startOfWeek = dateFormat.format(calendar.time)
        calendar.add(Calendar.DAY_OF_MONTH, 6)
        val endOfWeek = dateFormat.format(calendar.time)
        val lastWeek = "Last Week" to AnalyticsType.Weekly(startOfWeek, endOfWeek)

        // Last month
        calendar.time = Date()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val lastMonth = monthFormat.format(calendar.time)
        val lastMonthPair = "Last Month" to AnalyticsType.Monthly(lastMonth)

        // Order: Today → Yesterday → 2 Days Ago → 3 Days Ago → Last Week → Last Month
        return listOf(today) + lastDays + listOf(lastWeek, lastMonthPair)
    }

    fun formatDuration(milliseconds: Long): String {
        val totalSeconds = milliseconds / 1000
        val minutes = (totalSeconds / 60) % 60
        val hours = (totalSeconds / 3600) % 24
        val days = totalSeconds / (24 * 3600)

        return when {
            days > 0 -> "$days days ${if (hours > 0) "$hours hr" else ""}".trim()
            hours > 0 -> "$hours hr ${if (minutes > 0) "$minutes min" else ""}".trim()
            else -> "$minutes min"
        }
    }

}
