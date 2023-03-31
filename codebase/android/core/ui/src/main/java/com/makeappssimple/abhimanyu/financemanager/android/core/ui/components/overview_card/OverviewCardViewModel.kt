package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.overview_card

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.MyViewModel
import kotlinx.coroutines.flow.StateFlow

interface OverviewCardViewModel : MyViewModel {
    val overviewTabSelectionIndex: StateFlow<Int>
    val overviewCardData: StateFlow<OverviewCardData?>
    fun setOverviewTabSelectionIndex(
        updatedOverviewTabSelectionIndex: Int,
    )

    fun handleOverviewCardAction(
        overviewCardAction: OverviewCardAction,
    )
}
