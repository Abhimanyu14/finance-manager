package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen.TransactionsScreenUIData
import kotlinx.coroutines.flow.StateFlow

interface TransactionsScreenViewModel : ScreenViewModel {
    val myLogger: MyLogger
    val screenUIData: StateFlow<MyResult<TransactionsScreenUIData>?>

    val expenseCategories: StateFlow<List<Category>?>
    val incomeCategories: StateFlow<List<Category>?>
    val investmentCategories: StateFlow<List<Category>?>
    val sources: StateFlow<List<Source>?>
    val transactionForValues: StateFlow<List<TransactionFor>?>

    fun addToSelectedTransactions(
        transactionId: Int,
    )

    fun clearSelectedTransactions()

    fun navigateToAddTransactionScreen()

    fun navigateToViewTransactionScreen(
        transactionId: Int,
    )

    fun navigateUp()

    fun removeFromSelectedTransactions(
        transactionId: Int,
    )

    fun selectAllTransactions()

    fun toggleTransactionSelection(
        transactionId: Int,
    )

    fun updateSelectedFilter(
        updatedSelectedFilter: Filter,
    )

    fun updateSearchText(
        updatedSearchText: String,
    )

    fun updateSelectedSortOption(
        updatedSelectedSortOption: SortOption,
    )

    fun updateTransactionForValuesInTransactions(
        transactionForId: Int,
    )
}
