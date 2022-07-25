package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.components.TransactionsListItemViewData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TransactionsScreenViewModel : ScreenViewModel {
    val navigationManager: NavigationManager
    val expenseCategories: Flow<List<Category>>
    val incomeCategories: Flow<List<Category>>
    val sources: Flow<List<Source>>
    val transactionsListItemViewData: Flow<Map<String, List<TransactionsListItemViewData>>>
    val selectedExpenseCategoryIndices: StateFlow<List<Int>>
    val selectedIncomeCategoryIndices: StateFlow<List<Int>>
    val selectedSourceIndices: StateFlow<List<Int>>
    val selectedTransactionTypesIndices: StateFlow<List<Int>>
    val selectedSortOption: StateFlow<SortOption>
    val searchText: StateFlow<String>

    fun updateSelectedExpenseCategoryIndices(
        updatedSelectedExpenseCategoryIndices: List<Int>,
    )

    fun updateSelectedIncomeCategoryIndices(
        updatedSelectedIncomeCategoryIndices: List<Int>,
    )

    fun updateSelectedSourceIndices(
        updatedSelectedSourceIndices: List<Int>,
    )

    fun updateSelectedTransactionTypesIndices(
        updatedSelectedTransactionTypesIndices: List<Int>,
    )

    fun updateSearchText(
        updatedSearchText: String,
    )

    fun updateSelectedSortOption(
        updatedSelectedSortOption: SortOption,
    )

    fun deleteTransaction(
        id: Int,
    )
}
