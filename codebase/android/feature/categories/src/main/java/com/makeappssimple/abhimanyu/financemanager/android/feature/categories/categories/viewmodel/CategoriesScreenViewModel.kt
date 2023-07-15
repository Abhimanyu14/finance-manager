package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen.CategoriesScreenUIData
import kotlinx.coroutines.flow.StateFlow

interface CategoriesScreenViewModel : ScreenViewModel {
    val myLogger: MyLogger
    val screenUIData: StateFlow<MyResult<CategoriesScreenUIData>?>

    fun deleteCategory(
        id: Int,
    )

    fun navigateToAddCategoryScreen(
        transactionType: String,
    )

    fun navigateToEditCategoryScreen(
        categoryId: Int,
    )

    fun navigateUp()

    fun setDefaultCategoryIdInDataStore(
        defaultCategoryId: Int,
        transactionType: TransactionType,
    )

    fun updateSelectedTabIndex(
        updatedSelectedTabIndex: Int,
    )
}
