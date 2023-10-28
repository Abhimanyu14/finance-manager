package com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor

@Immutable
data class PieChartItemData(
    val text: String,
    val value: Float,
    val color: MyColor,
)
