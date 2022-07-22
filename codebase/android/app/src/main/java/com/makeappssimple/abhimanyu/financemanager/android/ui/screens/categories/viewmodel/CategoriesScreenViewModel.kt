package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CategoriesScreenViewModel : ScreenViewModel {
    val navigationManager: NavigationManager
    val selectedTabIndex: StateFlow<Int>
    val expenseCategories: Flow<List<Category>>
    val incomeCategories: Flow<List<Category>>
    val expenseCategoryIsUsedInTransactions: Flow<List<Boolean>>
    val incomeCategoryIsUsedInTransactions: Flow<List<Boolean>>
    val defaultExpenseCategoryId: Flow<Int?>
    val defaultIncomeCategoryId: Flow<Int?>

    fun deleteCategory(
        id: Int,
    )

    fun updateSelectedTabIndex(
        updatedSelectedTabIndex: Int,
    )

    fun setDefaultCategoryIdInDataStore(
        defaultCategoryId: Int,
        transactionType: TransactionType,
    )
}
