package am.android.parking.parking_lots.presentation

import am.android.parking.R
import am.android.parking.common_presentation.ui.theme.AvailableColor
import am.android.parking.common_presentation.ui.theme.OccupiedColor
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun ParkingLotItem(
    modifier: Modifier = Modifier,
    isAvailable: Boolean,
) {
    val tint = if (isAvailable) AvailableColor
    else OccupiedColor
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = if (isAvailable) R.drawable.ic_available else R.drawable.ic_occupied),
            contentDescription = null,
            modifier = modifier,
            tint = tint
        )
    }
}