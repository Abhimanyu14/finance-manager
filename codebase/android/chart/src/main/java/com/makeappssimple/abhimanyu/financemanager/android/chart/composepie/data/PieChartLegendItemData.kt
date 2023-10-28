package com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyColor

@Immutable
data class PieChartLegendItemData(
    val text: String,
    val color: MyColor,
)
