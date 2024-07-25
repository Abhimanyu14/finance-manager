package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.bottomsheet.TransactionsScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList

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
    val setIsInSelectionMode: (Boolean) -> Unit = {},
    val setScreenBottomSheetType: (TransactionsScreenBottomSheetType) -> Unit = {},
    val setSearchText: (updatedSearchText: String) -> Unit = {},
    val setSelectedFilter: (updatedSelectedFilter: Filter) -> Unit = {},
    val setSelectedSortOption: (updatedSelectedSortOption: SortOption) -> Unit = {},
    val updateTransactionForValuesInTransactions: (
        selectedTransactions: ImmutableList<Int>,
        transactionForId: Int,
    ) -> Unit = { _, _ -> },
) : ScreenUIStateEvents
