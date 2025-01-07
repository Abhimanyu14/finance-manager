package com.makeappssimple.abhimanyu.financemanager.android.cre.chart.composepie.data

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.theme.MyColor

@Immutable
public data class PieChartItemData(
    val text: String,
    val value: Float,
    val color: MyColor,
)
