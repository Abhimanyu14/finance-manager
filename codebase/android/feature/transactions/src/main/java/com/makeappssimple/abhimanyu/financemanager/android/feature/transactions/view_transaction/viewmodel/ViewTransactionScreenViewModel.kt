package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.StateFlow

interface ViewTransactionScreenViewModel : ScreenViewModel {
    val dateTimeUtil: DateTimeUtil
    val logger: Logger
    val navigationManager: NavigationManager
    val transactionData: StateFlow<TransactionData?>
    val originalTransactionData: StateFlow<TransactionData?>
    val refundTransactionData: StateFlow<List<TransactionData>>

    fun deleteTransaction(
        id: Int,
    )

    fun updateTransactionData()
}
