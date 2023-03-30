package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.Flow

interface TransactionForValuesScreenViewModel : ScreenViewModel {
    val navigationManager: NavigationManager
    val transactionForValuesIsUsedInTransactions: Flow<List<Boolean>>
    val transactionForValues: Flow<List<TransactionFor>>

    fun deleteTransactionFor(
        id: Int,
    )
}