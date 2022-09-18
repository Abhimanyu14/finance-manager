package com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class PieChartItemData(
    val text: String,
    val value: Float,
    val color: Color,
)
