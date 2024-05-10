package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen.TransactionsScreenUIData
import kotlinx.coroutines.flow.StateFlow

public interface TransactionsScreenViewModel : ScreenViewModel {
    public val screenUIData: StateFlow<MyResult<TransactionsScreenUIData>?>

    public fun addToSelectedTransactions(
        transactionId: Int,
    )

    public fun clearSelectedTransactions()

    public fun navigateToViewTransactionScreen(
        transactionId: Int,
    )

    public fun removeFromSelectedTransactions(
        transactionId: Int,
    )

    public fun navigateToAddTransactionScreen()

    public fun navigateUp()

    public fun selectAllTransactions()

    public fun updateTransactionForValuesInTransactions(
        transactionForId: Int,
    )

    public fun updateSearchText(
        updatedSearchText: String,
    )

    public fun updateSelectedFilter(
        updatedSelectedFilter: Filter,
    )

    public fun updateSelectedSortOption(
        updatedSelectedSortOption: SortOption,
    )
}
