package com.makeappssimple.abhimanyu.financemanager.android.core.chart.pie

import androidx.compose.runtime.Immutable

@Immutable
public data class PieChartData(
    val items: List<PieChartItemData>,
) {
    internal val totalSize: Float
        get() {
            return items.sumOf {
                it.value.toDouble()
            }.toFloat()
        }
}
