package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen.TransactionsScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen.TransactionsScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

public interface TransactionsScreenViewModel : ScreenViewModel {
    public val screenUIData: StateFlow<MyResult<TransactionsScreenUIData>?>

    public fun handleUIEvents(
        uiEvent: TransactionsScreenUIEvent,
    )

    public fun addToSelectedTransactions(
        transactionId: Int,
    )

    public fun clearSelectedTransactions()

    public fun navigateToViewTransactionScreen(
        transactionId: Int,
    )

    public fun removeFromSelectedTransactions(
        transactionId: Int,
    )
}
