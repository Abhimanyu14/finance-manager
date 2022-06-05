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
import androidx.compose.ui.tooling.preview.Preview
import com.makeappssimple.abhimanyu.financemanager.android.chart.pie.PieChartUtils.calculateAngle
import com.makeappssimple.abhimanyu.financemanager.android.chart.pie.renderer.SimpleSliceDrawer
import com.makeappssimple.abhimanyu.financemanager.android.chart.pie.renderer.SliceDrawer
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Blue
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Green
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Red

fun simpleChartAnimation() = TweenSpec<Float>(durationMillis = 500)

@Composable
fun PieChart(
    pieChartData: PieChartData,
    modifier: Modifier = Modifier,
    animation: AnimationSpec<Float> = simpleChartAnimation(),
    sliceDrawer: SliceDrawer = SimpleSliceDrawer(),
) {
    val transitionProgress = remember(pieChartData.items) { Animatable(initialValue = 0f) }

    // When slices value changes we want to re-animated the chart.
    LaunchedEffect(pieChartData.items) {
        transitionProgress.animateTo(1f, animationSpec = animation)
    }

    DrawChart(
        pieChartData = pieChartData,
        modifier = modifier.fillMaxSize(),
        progress = transitionProgress.value,
        sliceDrawer = sliceDrawer
    )
}

@Composable
private fun DrawChart(
    pieChartData: PieChartData,
    modifier: Modifier,
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

@Preview
@Composable
private fun PieChartPreview() = PieChart(
    pieChartData = PieChartData(
        items = listOf(
            PieChartItemData(
                value = 25f,
                color = Red,
            ),
            PieChartItemData(
                value = 42f,
                color = Blue,
            ),
            PieChartItemData(
                value = 23f,
                color = Green,
            )
        )
    )
)
