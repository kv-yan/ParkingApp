package am.android.parking.analytics.presentation

import am.android.parking.R
import am.android.parking.common_presentation.ui.theme.SubTitleColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
    animated: Boolean = true,
    valueComposable: @Composable (() -> Unit)? = null,
) {
    // Extract the first number in the string
    val numberMatch = remember(value) {
        Regex("""[\d.,]+""").find(value)
    }

    val rawNumber = numberMatch?.value?.replace(",", "")?.toFloatOrNull()
    val animatedNumber by animateFloatAsState(
        targetValue = if (animated && rawNumber != null) rawNumber else 0f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "AnimatedFloat"
    )

    // Replace number with animated number in original string
    val animatedValue = remember(animatedNumber, value) {
        if (animated && rawNumber != null) {
            val formatted = if (rawNumber % 1 == 0f) {
                animatedNumber.toInt().toString()
            } else {
                String.format("%.1f", animatedNumber)
            }
            value.replaceRange(numberMatch.range, formatted)
        } else {
            value
        }
    }

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

            if (valueComposable != null) {
                valueComposable()
            } else {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = animatedValue,
                    fontFamily = FontFamily(Font(R.font.gilroy_bold)),
                    fontSize = 28.sp,
                    color = valueColor
                )
            }
        }
    }
}
