package com.makeappssimple.abhimanyu.financemanager.android.ui.components.overview_card

import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

interface OverviewCardViewModel : BaseViewModel {
    val sourcesTotalBalanceAmountValue: Flow<Long>
}
