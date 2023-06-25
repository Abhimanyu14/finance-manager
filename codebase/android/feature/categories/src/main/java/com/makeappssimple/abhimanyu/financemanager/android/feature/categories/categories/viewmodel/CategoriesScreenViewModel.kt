package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen.CategoriesScreenUIData
import kotlinx.coroutines.flow.StateFlow

interface CategoriesScreenViewModel : ScreenViewModel {
    val logger: Logger
    val navigationManager: NavigationManager
    val screenUIData: StateFlow<MyResult<CategoriesScreenUIData>?>

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
