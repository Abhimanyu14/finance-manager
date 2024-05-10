package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen.ViewTransactionScreenUIData
import kotlinx.coroutines.flow.StateFlow

public interface ViewTransactionScreenViewModel : ScreenViewModel {
    public val screenUIData: StateFlow<MyResult<ViewTransactionScreenUIData>?>

    public fun deleteTransaction(
        transactionId: Int,
    )

    public fun initViewModel()

    public fun navigateToAddTransactionScreen(
        transactionId: Int,
    )

    public fun navigateToEditTransactionScreen(
        transactionId: Int,
    )

    public fun navigateToViewTransactionScreen(
        transactionId: Int,
    )

    public fun navigateUp()
}
