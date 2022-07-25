package com.makeappssimple.abhimanyu.financemanager.android.ui.components.total_balance_card

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.MyViewModel
import kotlinx.coroutines.flow.Flow

interface TotalBalanceCardViewModel : MyViewModel {
    val sourcesTotalBalanceAmountValue: Flow<Long>
}
