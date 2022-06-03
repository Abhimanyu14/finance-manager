package com.makeappssimple.abhimanyu.financemanager.android.chart.pie

import androidx.compose.ui.graphics.Color

data class PieChartItemData(
    val value: Float,
    val color: Color,
)

data class PieChartData(
    val items: List<PieChartItemData>,
) {
    internal val totalSize: Float
        get() {
            return items.sumOf {
                it.value.toDouble()
            }.toFloat()
        }
}
