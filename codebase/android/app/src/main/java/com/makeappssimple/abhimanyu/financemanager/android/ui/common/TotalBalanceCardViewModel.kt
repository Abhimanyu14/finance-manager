package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

interface TotalBalanceCardViewModel : BaseViewModel {
    val sourcesTotalBalanceAmountValue: Flow<Long>
}
