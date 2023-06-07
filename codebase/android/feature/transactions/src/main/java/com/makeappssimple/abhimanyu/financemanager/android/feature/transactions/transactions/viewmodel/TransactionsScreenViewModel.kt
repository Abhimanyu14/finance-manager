package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen.TransactionsScreenUIData
import kotlinx.coroutines.flow.StateFlow

interface TransactionsScreenViewModel : ScreenViewModel {
    val logger: Logger
    val navigationManager: NavigationManager
    val screenUIData: StateFlow<TransactionsScreenUIData?>

    val expenseCategories: StateFlow<List<Category>?>
    val incomeCategories: StateFlow<List<Category>?>
    val investmentCategories: StateFlow<List<Category>?>
    val sources: StateFlow<List<Source>?>

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
