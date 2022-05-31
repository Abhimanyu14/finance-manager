package com.makeappssimple.abhimanyu.financemanager.android.ui.components.overview_card

import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.data.PieChartData
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.StateFlow

interface OverviewCardViewModel : BaseViewModel {
    val pieChartData: StateFlow<PieChartData?>
}
