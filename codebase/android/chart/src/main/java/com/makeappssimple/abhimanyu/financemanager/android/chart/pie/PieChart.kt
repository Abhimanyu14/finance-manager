package com.makeappssimple.abhimanyu.financemanager.android.chart.pie

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import com.makeappssimple.abhimanyu.financemanager.android.chart.pie.PieChartUtils.calculateAngle
import com.makeappssimple.abhimanyu.financemanager.android.chart.pie.renderer.SimpleSliceDrawer
import com.makeappssimple.abhimanyu.financemanager.android.chart.pie.renderer.SliceDrawer

fun simpleChartAnimation() = TweenSpec<Float>(durationMillis = 500)

@Composable
fun PieChart(
    modifier: Modifier = Modifier,
    pieChartData: PieChartData,
    animation: AnimationSpec<Float> = simpleChartAnimation(),
    sliceDrawer: SliceDrawer = SimpleSliceDrawer(),
) {
    val transitionProgress = remember(pieChartData.items) { Animatable(initialValue = 0f) }

    // When slices value changes we want to re-animated the chart.
    LaunchedEffect(
        key1 = pieChartData.items,
    ) {
        transitionProgress.animateTo(
            targetValue = 1F,
            animationSpec = animation,
        )
    }

    DrawChart(
        pieChartData = pieChartData,
        modifier = modifier
            .fillMaxSize(),
        progress = transitionProgress.value,
        sliceDrawer = sliceDrawer
    )
}

@Composable
private fun DrawChart(
    pieChartData: PieChartData,
    modifier: Modifier = Modifier,
    progress: Float,
    sliceDrawer: SliceDrawer,
) {
    val slices = pieChartData.items

    Canvas(modifier = modifier) {
        drawIntoCanvas {
            var startArc = 0f

            slices.forEach { slice ->
                val arc = calculateAngle(
                    sliceLength = slice.value,
                    totalLength = pieChartData.totalSize,
                    progress = progress
                )

                sliceDrawer.drawSlice(
                    drawScope = this,
                    canvas = drawContext.canvas,
                    area = size,
                    startAngle = startArc,
                    sweepAngle = arc,
                    slice = slice
                )

                startArc += arc
            }
        }
    }
}
