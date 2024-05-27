package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Stable
internal class TransactionsScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setIsInSelectionMode: (Boolean) -> Unit,
    val setScreenBottomSheetType: (TransactionsScreenBottomSheetType) -> Unit,
    val setSearchText: (updatedSearchText: String) -> Unit,
    val setSelectedFilter: (updatedSelectedFilter: Filter) -> Unit,
    val setSelectedSortOption: (updatedSelectedSortOption: SortOption) -> Unit,
    val addToSelectedTransactions: (transactionId: Int) -> Unit,
    val removeFromSelectedTransactions: (transactionId: Int) -> Unit,
    val clearSelectedTransactions: () -> Unit,
    val selectAllTransactions: () -> Unit,
) : ScreenUIStateEvents
