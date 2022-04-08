package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories

import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

interface CategoriesViewModel : BaseViewModel {
    val navigationManager: NavigationManager
    val categories: Flow<List<Category>>

    fun deleteCategory(
        id: Int,
    )
}
