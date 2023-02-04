package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.view_transaction_for.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.StateFlow

interface ViewTransactionForScreenViewModel : ScreenViewModel {
    val navigationManager: NavigationManager
    val transactionForValues: StateFlow<List<TransactionFor>>
}
