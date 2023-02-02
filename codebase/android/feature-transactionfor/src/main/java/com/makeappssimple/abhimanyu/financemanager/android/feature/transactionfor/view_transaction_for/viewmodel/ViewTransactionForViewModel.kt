package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.view_transaction_for.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import kotlinx.coroutines.flow.StateFlow

interface ViewTransactionForViewModel {
    val navigationManager: NavigationManager
    val transactionForValues: StateFlow<List<TransactionFor>>
}
