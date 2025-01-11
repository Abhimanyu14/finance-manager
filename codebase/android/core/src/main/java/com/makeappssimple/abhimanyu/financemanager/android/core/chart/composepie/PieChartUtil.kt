package com.makeappssimple.abhimanyu.financemanager.android.core.chart.composepie

import androidx.compose.ui.geometry.Offset
import com.makeappssimple.abhimanyu.financemanager.android.core.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.core.chart.composepie.data.PieChartLegendItemData
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

private const val DEG2RAD = Math.PI / 180.0
internal const val FDEG2RAD = Math.PI.toFloat() / 180F
internal val FLOAT_EPSILON = Float.fromBits(1)

internal fun PieChartData.createLegendEntries(): List<PieChartLegendItemData> {
    return items.map { item ->
        PieChartLegendItemData(
            text = item.text,
            color = item.color,
        )
    }
}

internal fun PieChartData.calculateFractions(): List<Float> {
    val total = items
        .sumOf {
            it.value.toDouble()
        }.toFloat()
    if (total == 0F) {
        return items
            .map {
                0F
            }
    }
    val fractions = items
        .map {
            (it.value / total) * 360F
        }
    return fractions
}

internal fun calculateMinimumRadiusForSpacedSlice(
    center: Offset,
    radius: Float,
    angle: Float,
    arcStartPointX: Float,
    arcStartPointY: Float,
    startAngle: Float,
    sweepAngle: Float,
): Float {
    val angleMiddle = startAngle + sweepAngle / 2F

    // Other point of the arc
    val arcEndPointX: Float = center.x + radius * cos((startAngle + sweepAngle) * FDEG2RAD)
    val arcEndPointY: Float = center.y + radius * sin((startAngle + sweepAngle) * FDEG2RAD)

    // Middle point on the arc
    val arcMidPointX: Float = center.x + radius * cos(angleMiddle * FDEG2RAD)
    val arcMidPointY: Float = center.y + radius * sin(angleMiddle * FDEG2RAD)

    // This is the base of the contained triangle
    val basePointsDistance = sqrt(
        (arcEndPointX - arcStartPointX).toDouble().pow(2.0) +
                (arcEndPointY - arcStartPointY).toDouble().pow(2.0)
    )

    // After reducing space from both sides of the "slice",
    //   the angle of the contained triangle should stay the same.
    // So let's find out the height of that triangle.
    val containedTriangleHeight =
        (basePointsDistance / 2.0 * tan((180.0 - angle) / 2.0 * DEG2RAD)).toFloat()

    // Now we subtract that from the radius
    var spacedRadius = radius - containedTriangleHeight

    // And now subtract the height of the arc that's between the triangle and the outer circle
    spacedRadius -= sqrt(
        (arcMidPointX - (arcEndPointX + arcStartPointX) / 2F).toDouble().pow(2.0) +
                (arcMidPointY - (arcEndPointY + arcStartPointY) / 2F).toDouble().pow(2.0)
    ).toFloat()

    return spacedRadius
}
