package com.makeappssimple.abhimanyu.financemanager.android.chart.composepie

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.util.fastForEachIndexed
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

private const val START_DEGREE = -90F

@Composable
internal fun PieChartRenderer(
    modifier: Modifier = Modifier,
    chartSizePx: Float,
    sliceWidthPx: Float,
    sliceSpacingPx: Float,
    fractions: List<Float>,
    composeColors: List<Color>,
    animate: Boolean,
) {
    val emptyChartColor = MaterialTheme.colorScheme.surfaceVariant
    var animationRan by rememberSaveable(fractions, animate) {
        mutableStateOf(
            value = false,
        )
    }
    val animation = remember {
        Animatable(
            initialValue = when {
                animate -> {
                    if (animationRan) {
                        1F
                    } else {
                        0F
                    }
                }

                else -> {
                    1F
                }
            },
        )
    }
    val phase by animation.asState()
    val pathBuffer by remember {
        mutableStateOf(
            value = Path(),
        )
    }
    val holeRadius = remember(
        key1 = chartSizePx,
        key2 = sliceWidthPx,
    ) {
        (chartSizePx - (sliceWidthPx * 2F)) / 2F
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        if (!animationRan) {
            animation.animateTo(
                targetValue = 1F,
                animationSpec = tween(
                    durationMillis = 325,
                ),
            ) {
                animationRan = true
            }
        }
    }

    Canvas(
        modifier = modifier,
    ) {
        val circleBox = Rect(
            left = 0F,
            top = 0F,
            right = size.width,
            bottom = size.height,
        )
        var angle = 0F
        val outerRadius = chartSizePx / 2F
        val drawInnerArc = sliceWidthPx > FLOAT_EPSILON && sliceWidthPx < chartSizePx / 2F
        val userInnerRadius = if (drawInnerArc) {
            holeRadius
        } else {
            0F
        }

        val total = fractions.sum()
        val isTotalZero = total == 0F
        val finalFractions = if (isTotalZero) {
            listOf(360F)
        } else {
            fractions
        }

        finalFractions.fastForEachIndexed { index, sliceAngle ->
            var innerRadius = userInnerRadius
            val accountForSliceSpacing = sliceSpacingPx > 0F && sliceAngle <= 180F
            val sliceSpaceAngleOuter = sliceSpacingPx / (FDEG2RAD * outerRadius)
            val startAngleOuter = START_DEGREE + (angle + sliceSpaceAngleOuter / 2F) * phase
            val sweepAngleOuter = (sliceAngle - sliceSpaceAngleOuter) * phase
            pathBuffer.reset()
            val arcStartPointX = center.x + outerRadius * cos(startAngleOuter * FDEG2RAD)
            val arcStartPointY = center.y + outerRadius * sin(startAngleOuter * FDEG2RAD)
            if (sweepAngleOuter >= 360F && sweepAngleOuter % 360F <= FLOAT_EPSILON) {
                // Android is doing "mod 360"
                pathBuffer.addArc(
                    oval = circleBox,
                    startAngleDegrees = START_DEGREE,
                    sweepAngleDegrees = sweepAngleOuter,
                )
            } else {
                pathBuffer.arcTo(
                    rect = circleBox,
                    startAngleDegrees = startAngleOuter,
                    sweepAngleDegrees = sweepAngleOuter,
                    forceMoveTo = false,
                )
            }
            val innerRectBuffer = Rect(
                left = center.x - innerRadius,
                top = center.y - innerRadius,
                right = center.x + innerRadius,
                bottom = center.y + innerRadius,
            )
            if (drawInnerArc && (innerRadius > 0F || accountForSliceSpacing)) {
                if (accountForSliceSpacing) {
                    val minSpacedRadius = calculateMinimumRadiusForSpacedSlice(
                        center = center,
                        radius = outerRadius,
                        angle = sliceAngle * phase,
                        arcStartPointX = arcStartPointX,
                        arcStartPointY = arcStartPointY,
                        startAngle = startAngleOuter,
                        sweepAngle = sweepAngleOuter,
                    ).absoluteValue

                    innerRadius = max(innerRadius, minSpacedRadius)
                }

                val sliceSpaceAngleInner = sliceSpacingPx / (FDEG2RAD * innerRadius)

                val startAngleInner = START_DEGREE + (angle + sliceSpaceAngleInner / 2F) * phase
                val sweepAngleInner = ((sliceAngle - sliceSpaceAngleInner) * phase).coerceAtLeast(
                    minimumValue = 0F,
                )

                val endAngleInner = startAngleInner + sweepAngleInner
                if (sweepAngleOuter >= 360F && sweepAngleOuter % 360F <= FLOAT_EPSILON) {
                    // Android is doing "mod 360"
                    pathBuffer.addArc(
                        oval = innerRectBuffer,
                        startAngleDegrees = START_DEGREE,
                        sweepAngleDegrees = sweepAngleOuter,
                    )
                } else {
                    pathBuffer.lineTo(
                        x = center.x + innerRadius * cos(endAngleInner * FDEG2RAD),
                        y = center.y + innerRadius * sin(endAngleInner * FDEG2RAD),
                    )

                    pathBuffer.arcTo(
                        rect = innerRectBuffer,
                        startAngleDegrees = endAngleInner,
                        sweepAngleDegrees = -sweepAngleInner,
                        forceMoveTo = false,
                    )
                }
            } else {
                if (sweepAngleOuter % 360F > FLOAT_EPSILON) {
                    if (accountForSliceSpacing) {
                        val angleMiddle = startAngleOuter + sweepAngleOuter / 2F
                        val sliceSpaceOffset = calculateMinimumRadiusForSpacedSlice(
                            center = center,
                            radius = outerRadius,
                            angle = sliceAngle * phase,
                            arcStartPointX = arcStartPointX,
                            arcStartPointY = arcStartPointY,
                            startAngle = startAngleOuter,
                            sweepAngle = sweepAngleOuter,
                        )
                        val arcEndPointX = center.x + sliceSpaceOffset * cos(angleMiddle * FDEG2RAD)
                        val arcEndPointY = center.y + sliceSpaceOffset * sin(angleMiddle * FDEG2RAD)
                        pathBuffer.lineTo(
                            x = arcEndPointX,
                            y = arcEndPointY,
                        )
                    } else {
                        pathBuffer.lineTo(
                            x = center.x,
                            y = center.y,
                        )
                    }
                }
            }
            pathBuffer.close()
            drawPath(
                path = pathBuffer,
                color = if (isTotalZero) {
                    emptyChartColor
                } else {
                    composeColors[index]
                },
            )
            angle += sliceAngle * phase
        }
    }
}
