package am.android.parking.diagrams.presentation


import am.android.parking.common_presentation.ui.theme.ValueColor
import am.android.parking.diagrams.domain.model.ParkingOccupancyData
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedBars(
    modifier: Modifier = Modifier,
    data: List<ParkingOccupancyData>,
) {
    var animationTriggered by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(-1) }

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

    // Animate selection state
    val selectionAnimations = data.mapIndexed { index, _ ->
        animateFloatAsState(
            targetValue = if (selectedIndex == index) 1.1f else 1f,
            animationSpec = spring(stiffness = 300f),
            label = "selection_$index"
        )
    }

    LaunchedEffect(Unit) {
        animationTriggered = true
    }

    Box(modifier = modifier.height(200.dp)) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .pointerInput(Unit) {
                    detectTapGestures { tapOffset ->
                        val barWidth = size.width / (data.size * 1.5f)
                        val spacing = barWidth * 0.5f

                        val tappedIndex = ((tapOffset.x) / (barWidth + spacing)).toInt()
                        selectedIndex = if (tappedIndex in data.indices) tappedIndex else -1
                    }
                }
        ) {
            val height = size.height
            val width = size.width
            val barWidth = width / (data.size * 1.5f)
            val spacing = barWidth * 0.5f

            data.forEachIndexed { index, item ->
                val left = index * (barWidth + spacing)
                val barHeight = height * animatedHeights[index].value
                val scale = selectionAnimations[index].value
                val barLeft = left + (barWidth * (1 - scale)) / 2
                val barTop = height - barHeight * scale

                drawRoundRect(
                    color = ValueColor.copy(alpha = 0.7f + item.occupancyPercentage * 0.3f),
                    topLeft = Offset(barLeft, barTop),
                    size = Size(barWidth * scale, barHeight * scale),
                    cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                )

                if (selectedIndex == index) {
                    val revenueText = item.formattedRevenue

                    drawIntoCanvas { canvas ->
                        val textPaint = android.graphics.Paint().apply {
                            color = android.graphics.Color.WHITE
                            textSize = 13.sp.toPx()
                            textAlign = android.graphics.Paint.Align.LEFT
                            isAntiAlias = true
                        }

                        // Measure text width and height
                        val textWidth = textPaint.measureText(revenueText)
                        val textHeight = textPaint.fontMetrics.run { bottom - top }

                        val padding = 8.dp.toPx()
                        val cornerRadius = 6.dp.toPx()

                        val tooltipWidth = textWidth + padding * 2
                        val tooltipHeight = textHeight + padding * 2
                        val tooltipLeft = barLeft + (barWidth * scale - tooltipWidth) / 2
                        val tooltipTop = barTop - tooltipHeight - 12.dp.toPx()

                        // Draw white rounded rectangle (tooltip box)
                        val backgroundPaint = android.graphics.Paint().apply {
                            color = android.graphics.Color.BLACK
                            isAntiAlias = true
                        }

                        canvas.nativeCanvas.drawRoundRect(
                            tooltipLeft,
                            tooltipTop,
                            tooltipLeft + tooltipWidth,
                            tooltipTop + tooltipHeight,
                            cornerRadius,
                            cornerRadius,
                            backgroundPaint
                        )

                        // Draw triangle (pointer)
                        val trianglePath = android.graphics.Path().apply {
                            val triangleSize = 10.dp.toPx()
                            moveTo(tooltipLeft + tooltipWidth / 2 - triangleSize / 2, tooltipTop + tooltipHeight)
                            lineTo(tooltipLeft + tooltipWidth / 2 + triangleSize / 2, tooltipTop + tooltipHeight)
                            lineTo(tooltipLeft + tooltipWidth / 2, tooltipTop + tooltipHeight + triangleSize)
                            close()
                        }

                        canvas.nativeCanvas.drawPath(trianglePath, backgroundPaint)

                        // Draw the revenue text (black text)
                        canvas.nativeCanvas.drawText(
                            revenueText,
                            tooltipLeft + padding,
                            tooltipTop + padding - textPaint.fontMetrics.top,
                            textPaint
                        )
                    }
                }
            }
        }
    }
}