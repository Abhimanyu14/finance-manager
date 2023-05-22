package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

interface TransactionsScreenViewModel : ScreenViewModel {
    val logger: Logger
    val navigationManager: NavigationManager
    val sortOptions: List<SortOption>
    val transactionDetailsListItemViewData: Flow<Map<String, List<TransactionListItemData>>>
    val transactionTypes: List<TransactionType>
    val currentLocalDate: LocalDate
    val currentTimeMillis: Long
    val oldestTransactionLocalDate: StateFlow<LocalDate>
    val isLoading: StateFlow<Boolean>
    val expenseCategories: StateFlow<List<Category>?>
    val incomeCategories: StateFlow<List<Category>?>
    val investmentCategories: StateFlow<List<Category>?>
    val selectedFilter: StateFlow<Filter>
    val selectedSortOption: StateFlow<SortOption>
    val sources: StateFlow<List<Source>?>
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
}
