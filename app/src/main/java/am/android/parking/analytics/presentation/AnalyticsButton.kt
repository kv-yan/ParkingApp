package am.android.parking.analytics.presentation

import am.android.parking.R
import am.android.parking.common_presentation.ui.theme.MainBlue
import am.android.parking.common_presentation.ui.theme.Shape8
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@Composable
fun AnalyticsButton(
    modifier: Modifier = Modifier,
    isCurrent: Boolean = false,
    text: String,
    onClick: () -> Unit,
) {
    val backgroundColor = if (isCurrent) MainBlue else Color.White
    val textColor = if (isCurrent) Color.White else Color.Black

    Box(
        modifier = modifier
            .background(backgroundColor, shape = Shape8)
            .border(width = 1.dp, color = MainBlue, shape = Shape8)
            .clickable { onClick() }) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = text,
            color = textColor,
            fontFamily = FontFamily(Font(R.font.gilroy_bold)),
        )
    }
}