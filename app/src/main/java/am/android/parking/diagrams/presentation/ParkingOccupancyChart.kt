package am.android.parking.diagrams.presentation

import am.android.parking.R
import am.android.parking.common_presentation.ui.theme.Gray600
import am.android.parking.common_presentation.ui.theme.Shape8
import am.android.parking.common_presentation.ui.theme.SubTitleColor
import am.android.parking.common_presentation.ui.theme.ValueColor
import am.android.parking.diagrams.domain.model.ParkingOccupancyData
import am.android.parking.diagrams.domain.model.mockParkingData
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ParkingOccupancyChart(
    modifier: Modifier = Modifier,
    data: List<ParkingOccupancyData> = mockParkingData,
) {
    val primaryColor = ValueColor
    val onSurfaceVariant = ValueColor

    Box(
        modifier = modifier
            .padding(16.dp)
            .background(Color.White, Shape8)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = stringResource(R.string.parking_occupancy_last_12_months),
                fontFamily = FontFamily(Font(R.font.gilroy_semi_bold)),
                fontSize = 16.sp,
                color = Gray600,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Y-axis labels and chart
            Row {
                // Y-axis labels
                Column(
                    modifier = Modifier
                        .width(35.dp)
                        .height(200.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("100%", "80%", "60%", "40%", "20%").forEach { label ->
                        Text(
                            text = label,
                            fontFamily = FontFamily(Font(R.font.inter_regular)),
                            fontSize = 12.sp,
                            color = SubTitleColor,
                            modifier = Modifier
                                .height(40.dp)
                                .fillMaxWidth()
                        )
                    }
                }

                // Chart area
                AnimatedBars(data = data)
            }

            // X-axis labels
            Row(
                modifier = Modifier
                    .padding(start = 40.dp)
                    .fillMaxWidth()
            ) {
                data.forEach { item ->
                    Text(
                        text = item.month,
                        fontFamily = FontFamily(Font(R.font.inter_regular)),
                        fontSize = 10.sp,
                        color = SubTitleColor,
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Diagram() {
    ParkingOccupancyChart()
}