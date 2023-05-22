package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.MyViewModel
import kotlinx.coroutines.flow.Flow

interface TotalBalanceCardViewModel : MyViewModel {
    val sourcesTotalBalanceAmountValue: Flow<Long>
}
