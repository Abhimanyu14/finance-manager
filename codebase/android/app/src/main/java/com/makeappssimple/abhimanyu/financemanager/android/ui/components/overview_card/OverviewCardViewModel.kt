package com.makeappssimple.abhimanyu.financemanager.android.ui.components.overview_card

import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.MyViewModel
import kotlinx.coroutines.flow.StateFlow

interface OverviewCardViewModel : MyViewModel {
    val overviewTabSelectionIndex: StateFlow<Int>
    val pieChartData: StateFlow<com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData?>
    fun setOverviewTabSelectionIndex(
        updatedOverviewTabSelectionIndex: Int,
    )
}
