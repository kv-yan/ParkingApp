package am.android.parking.parking_lots.presentation

import am.android.parking.R
import am.android.parking.common_presentation.ui.theme.MainRed
import am.android.parking.common_presentation.ui.theme.SubTitleColor
import am.android.parking.common_presentation.ui.theme.ValueColor
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel

@Composable
fun ParkingLotsSection(
    modifier: Modifier = Modifier,
    viewModel: ParkingLotViewModel = koinViewModel(),
) {
    val lotsStatus by viewModel.parkingLotStatus.collectAsState()
    val context = LocalContext.current
    val config = context.resources.configuration
    val screenWidth = config.screenWidthDp

    Text(
        modifier = modifier,
        text = "Now",
        fontFamily = FontFamily(Font(R.font.gilroy_bold)),
        fontSize = 16.sp,
        color = SubTitleColor
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.padding(start = 24.dp, top = 16.dp, end = 30.dp ,bottom = 8.dp),
                text = "Parking Lots Occupied",
                fontFamily = FontFamily(Font(R.font.gilroy_bold)),
                fontSize = 16.sp,
                color = SubTitleColor
            )
            Text(
                modifier = Modifier.padding(horizontal = 32.dp), text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MainRed)) {
                        append("${lotsStatus?.occupiedSpots}")
                    }
                    withStyle(style = SpanStyle(color = ValueColor)) {
                        append(" / ${lotsStatus?.totalSpots}")
                    }
                }, style = TextStyle(
                    fontSize = 20.88.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy_bold)),
                    fontWeight = FontWeight(400)
                )
            )

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                thickness = 1.dp,
                color = SubTitleColor.copy(0.7f)
            )
            Text(
                modifier = Modifier.padding(start = 24.dp, top = 12.dp, bottom = 8.dp),
                text = "Parking Occupancy",
                style = TextStyle(
                    fontSize = 15.66.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy_bold)),
                    color = SubTitleColor,
                    letterSpacing = 0.02.sp,
                )
            )

            LazyVerticalGrid(
                modifier = Modifier
                    .width(screenWidth.dp)
                    .padding(bottom = 16.dp)
                    .heightIn(min = 0.dp, max = 600.dp),
                columns = GridCells.Fixed(4),
            ) {
                items(lotsStatus?.spots?.size ?: 0) { index ->
                    ParkingLotItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        isAvailable = lotsStatus?.spots?.get(index)?.isAvailable ?: false
                    )
                }
            }
        }
    }
}