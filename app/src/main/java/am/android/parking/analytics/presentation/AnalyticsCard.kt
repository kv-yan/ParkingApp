package am.android.parking.analytics.presentation

import am.android.parking.R
import am.android.parking.common_presentation.ui.theme.SubTitleColor
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnalyticsCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String = "",
    valueColor: Color = Color.Black,
    valueComposable: @Composable (() -> Unit)? = null,

    ) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                text = title,
                fontFamily = FontFamily(Font(R.font.gilroy_bold)),
                fontSize = 16.sp,
                color = SubTitleColor
            )

            if (valueComposable != null) valueComposable()
            else Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = value,
                fontFamily = FontFamily(Font(R.font.gilroy_bold)),
                fontSize = 28.sp,
                color = valueColor
            )
        }
    }
}