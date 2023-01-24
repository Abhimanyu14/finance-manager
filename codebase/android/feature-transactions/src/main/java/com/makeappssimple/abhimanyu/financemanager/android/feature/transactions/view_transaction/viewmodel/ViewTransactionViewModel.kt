package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import kotlinx.coroutines.flow.StateFlow

interface ViewTransactionViewModel {
    val navigationManager: NavigationManager
    val transactionData: StateFlow<TransactionData?>
    val originalTransactionData: StateFlow<TransactionData?>
    val refundTransactionData: StateFlow<List<TransactionData>>

    fun deleteTransaction(
        id: Int,
    )

    fun updateTransactionData()
}
