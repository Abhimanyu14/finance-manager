package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories

import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CategoriesViewModel : BaseViewModel {
    val navigationManager: NavigationManager
    val selectedTabIndex: StateFlow<Int>
    val filteredCategories: Flow<List<Category>>
    val categoriesIsUsedInTransactions: Flow<List<Boolean>>

    fun deleteCategory(
        id: Int,
    )

    fun updateSelectedTabIndex(
        updatedSelectedTabIndex: Int,
    )
}
