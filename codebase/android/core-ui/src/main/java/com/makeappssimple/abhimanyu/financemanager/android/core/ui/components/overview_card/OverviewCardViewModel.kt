package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.overview_card

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.MyViewModel
import kotlinx.coroutines.flow.StateFlow

interface OverviewCardViewModel : MyViewModel {
    val overviewTabSelectionIndex: StateFlow<Int>
    val amountData: StateFlow<List<Float>?>
    fun setOverviewTabSelectionIndex(
        updatedOverviewTabSelectionIndex: Int,
    )
}
