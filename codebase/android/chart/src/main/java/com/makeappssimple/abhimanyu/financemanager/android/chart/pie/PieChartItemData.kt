package com.makeappssimple.abhimanyu.financemanager.android.chart.pie

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class PieChartItemData(
    val value: Float,
    val color: Color,
)