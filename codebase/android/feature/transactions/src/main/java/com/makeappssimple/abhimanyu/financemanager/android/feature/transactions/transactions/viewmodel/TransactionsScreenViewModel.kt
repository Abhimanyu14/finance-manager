package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen.TransactionsScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen.TransactionsScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

interface TransactionsScreenViewModel : ScreenViewModel {
    val screenUIData: StateFlow<MyResult<TransactionsScreenUIData>?>

    fun handleUIEvents(
        uiEvent: TransactionsScreenUIEvent,
    )
}
