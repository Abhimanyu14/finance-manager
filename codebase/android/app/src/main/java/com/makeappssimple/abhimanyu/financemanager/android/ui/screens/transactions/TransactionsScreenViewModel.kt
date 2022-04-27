package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions

import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseScreenViewModel
import kotlinx.coroutines.flow.Flow

interface TransactionsScreenViewModel : BaseScreenViewModel {
    val navigationManager: NavigationManager
    val transactionsListItemViewData: Flow<List<TransactionsListItemViewData>>

    fun deleteTransaction(
        id: Int,
    )
}
