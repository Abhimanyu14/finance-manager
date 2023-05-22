package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData
import kotlinx.coroutines.flow.StateFlow

interface ViewTransactionScreenViewModel : ScreenViewModel {
    val logger: Logger
    val navigationManager: NavigationManager
    val originalTransactionListItemData: StateFlow<TransactionListItemData?>
    val refundTransactionListItemData: StateFlow<List<TransactionListItemData>>
    val transactionListItemData: StateFlow<TransactionListItemData?>

    fun getTransactionData()

    fun deleteTransaction(
        id: Int,
    )
}
