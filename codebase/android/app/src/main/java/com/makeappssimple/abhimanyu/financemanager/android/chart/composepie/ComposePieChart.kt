package com.makeappssimple.abhimanyu.financemanager.android.chart.composepie

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartItemData
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.legend.PieChartLegend
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.CenterBox
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Black
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Blue
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Green
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.MyAppTheme
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Red

/**
 * Source - https://github.com/humawork/compose-charts
 */
@Composable
fun ComposePieChart(
    data: PieChartData,
) {
    val fractions = remember(
        key1 = data,
    ) {
        data.calculateFractions()
    }
    val legendEntries = remember(
        key1 = data,
    ) {
        data.createLegendEntries()
    }
    val chartSize: Dp = 100.dp
    val chartSizePx = with(
        receiver = LocalDensity.current,
    ) {
        chartSize.toPx()
    }
    val sliceWidthPx = with(
        receiver = LocalDensity.current,
    ) {
        16.dp.toPx()
    }
    val sliceSpacingPx = with(
        receiver = LocalDensity.current,
    ) {
        2.dp.toPx()
    }
    val entryColors = data.items.map {
        it.color
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
    ) {
        PieChartRenderer(
            modifier = Modifier
                .requiredSize(
                    size = chartSize,
                ),
            chartSizePx = chartSizePx,
            sliceWidthPx = sliceWidthPx,
            sliceSpacingPx = sliceSpacingPx,
            fractions = fractions,
            composeColors = entryColors,
            animate = true,
        )
        PieChartLegend(
            items = legendEntries,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                ),
        )
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun ComposePieChartPreview() {
    MyAppTheme {
        CenterBox {
            ComposePieChart(
                data = PieChartData(
                    items = listOf(
                        PieChartItemData(
                            text = "Red",
                            value = 1F,
                            color = Red,
                        ),
                        PieChartItemData(
                            text = "Blue",
                            value = 2F,
                            color = Blue,
                        ),
                        PieChartItemData(
                            text = "Green",
                            value = 3F,
                            color = Green,
                        ),
                        PieChartItemData(
                            text = "Black",
                            value = 4F,
                            color = Black,
                        ),
                    )
                )
            )
        }
    }
}
