package am.android.parking.diagrams.presentation


import am.android.parking.common_presentation.ui.theme.ValueColor
import am.android.parking.diagrams.domain.model.ParkingOccupancyData
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedBars(data: List<ParkingOccupancyData>) {
    var animationTriggered by remember { mutableStateOf(false) }

    // Calculate target heights
    val targetHeights = remember(data) {
        data.map { it.occupancyPercentage }
    }

    // Create animated values
    val animatedHeights = targetHeights.mapIndexed { index, target ->
        animateFloatAsState(
            targetValue = if (animationTriggered) target else 0f,
            animationSpec = spring(
                dampingRatio = 0.5f,
                stiffness = 300f,
                visibilityThreshold = 0.01f
            ),
            label = "bar_$index"
        )
    }

    LaunchedEffect(Unit) {
        animationTriggered = true
    }

    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)) {
        val height = size.height
        val width = size.width
        val barWidth = width / (data.size * 1.5f)
        val spacing = barWidth * 0.5f

        data.forEachIndexed { index, data ->
            val left = index * (barWidth + spacing)
            val barHeight = height * animatedHeights[index].value

            drawRoundRect(
                color = ValueColor.copy(alpha =  + data.occupancyPercentage ),
                topLeft = Offset(left, height - barHeight),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
            )
        }
    }
}