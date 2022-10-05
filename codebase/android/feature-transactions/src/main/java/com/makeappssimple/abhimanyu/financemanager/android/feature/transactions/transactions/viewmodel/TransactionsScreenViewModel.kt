package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TransactionsScreenViewModel : ScreenViewModel {
    val navigationManager: NavigationManager
    val expenseCategories: Flow<List<Category>>
    val incomeCategories: Flow<List<Category>>
    val investmentCategories: Flow<List<Category>>
    val sources: Flow<List<Source>>
    val transactionDetailsListItemViewData: Flow<Map<String, List<TransactionData>>>
    val isLoading: StateFlow<Boolean>
    val selectedFilter: StateFlow<Filter>
    val selectedSortOption: StateFlow<SortOption>
    val searchText: StateFlow<String>

    fun updateSelectedFilter(
        updatedSelectedFilter: Filter,
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
