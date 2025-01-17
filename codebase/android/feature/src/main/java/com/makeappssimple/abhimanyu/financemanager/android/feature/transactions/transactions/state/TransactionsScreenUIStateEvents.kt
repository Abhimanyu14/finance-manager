package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.bottomsheet.TransactionsScreenBottomSheetType

@Stable
internal class TransactionsScreenUIStateEvents(
    val addToSelectedTransactions: (transactionId: Int) -> Unit = {},
    val clearSelectedTransactions: () -> Unit = {},
    val navigateToAddTransactionScreen: () -> Unit = {},
    val navigateToViewTransactionScreen: (transactionId: Int) -> Unit = {},
    val navigateUp: () -> Unit = {},
    val removeFromSelectedTransactions: (transactionId: Int) -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val selectAllTransactions: () -> Unit = {},
    val updateIsInSelectionMode: (Boolean) -> Unit = {},
    val updateScreenBottomSheetType: (TransactionsScreenBottomSheetType) -> Unit = {},
    val updateSearchText: (updatedSearchText: String) -> Unit = {},
    val updateSelectedFilter: (updatedSelectedFilter: Filter) -> Unit = {},
    val updateSelectedSortOption: (updatedSelectedSortOption: SortOption) -> Unit = {},
    val updateTransactionForValuesInTransactions: (transactionForId: Int) -> Unit = {},
) : ScreenUIStateEvents
