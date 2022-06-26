package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions

import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.Flow

interface TransactionsScreenViewModel : ScreenViewModel {
    val navigationManager: NavigationManager
    val categories: Flow<List<Category>>
    val sources: Flow<List<Source>>
    val transactionsListItemViewData: Flow<List<TransactionsListItemViewData>>

    fun deleteTransaction(
        id: Int,
    )
}
