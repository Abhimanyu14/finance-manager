package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CategoriesScreenViewModel : ScreenViewModel {
    val logger: Logger
    val navigationManager: NavigationManager
    val selectedTabIndex: StateFlow<Int>
    val expenseCategories: Flow<List<Category>>
    val incomeCategories: Flow<List<Category>>
    val investmentCategories: Flow<List<Category>>
    val expenseCategoryIsUsedInTransactions: Flow<List<Boolean>>
    val incomeCategoryIsUsedInTransactions: Flow<List<Boolean>>
    val investmentCategoryIsUsedInTransactions: Flow<List<Boolean>>
    val defaultExpenseCategoryId: Flow<Int?>
    val defaultIncomeCategoryId: Flow<Int?>
    val defaultInvestmentCategoryId: Flow<Int?>

    fun deleteCategory(
        id: Int,
    )

    fun setDefaultCategoryIdInDataStore(
        defaultCategoryId: Int,
        transactionType: TransactionType,
    )

    fun updateSelectedTabIndex(
        updatedSelectedTabIndex: Int,
    )
}
