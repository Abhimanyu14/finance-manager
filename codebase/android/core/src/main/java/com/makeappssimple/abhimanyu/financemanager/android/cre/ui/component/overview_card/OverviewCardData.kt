package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.overview_card

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.cre.chart.composepie.data.PieChartData

@Immutable
public data class OverviewCardData(
    val isLoading: Boolean = false,
    val overviewTabSelectionIndex: Int = 0,
    val title: String = "",
    val pieChartData: PieChartData? = null,
)
