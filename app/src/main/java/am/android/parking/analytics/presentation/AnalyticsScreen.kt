package am.android.parking.analytics.presentation

import am.android.parking.R
import am.android.parking.common_presentation.ui.theme.ScreenBackground
import am.android.parking.common_presentation.ui.theme.SubTitleColor
import am.android.parking.common_presentation.ui.theme.ValueColor
import am.android.parking.parking_lots.presentation.ParkingLotsSection
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel

@Composable
fun AnalyticsScreen(
    modifier: Modifier = Modifier,
    viewModel: AnalyticsViewModel = koinViewModel(),
    onNavigateToSettings: () -> Unit = {},
    onNavigateToDiagrams: () -> Unit = {},
) {
    val analytics by viewModel.analytics.collectAsState()
    val timePeriods by viewModel.timePeriods.collectAsState()
    val analyticsShowingType by viewModel.analyticsShowingType.collectAsState()

    val horizontalScroll = rememberScrollState()
    val mainVerticalScroll = rememberScrollState()


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
            .verticalScroll(mainVerticalScroll)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                text = stringResource(R.string.choose_a_time_period),
                fontFamily = FontFamily(Font(R.font.gilroy_semi_bold)),
                fontSize = 16.sp,
                color = SubTitleColor,
            )

            IconButton(
                modifier = Modifier, onClick = onNavigateToSettings
            ) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = stringResource(R.string.settings),
                    tint = SubTitleColor
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(horizontalScroll)
                .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            timePeriods.forEach {
                AnalyticsButton(
                    text = it.first,
                    isCurrent = analyticsShowingType == it.second,
                    onClick = { viewModel.setShowingType(it.second) }
                )
            }

            AnalyticsButton(
                text = stringResource(R.string.by_month),
                isCurrent = false,
                onClick = onNavigateToDiagrams
            )

        }

        AnalyticsCard(
            modifier = Modifier.padding(vertical = 16.dp),
            title = stringResource(R.string.total_revenue),
            value = stringResource(R.string.currency, analytics?.totalRevenue ?: 0),
            valueColor = ValueColor
        )

        AnalyticsCard(
            modifier = Modifier.padding(bottom = 16.dp),
            title = stringResource(R.string.total_cars_parked),
            value = stringResource(R.string.cars_quantity, analytics?.carsParked ?: 0),
            valueColor = ValueColor
        )

        AnalyticsCard(
            title = stringResource(R.string.average_parking_time),
            value = viewModel.formatDuration(analytics?.averageParkingTime ?: 0),
            valueColor = ValueColor
        )

        ParkingLotsSection(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        )
    }
}