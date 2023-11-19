package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
sealed class CategoriesScreenUIEvent : ScreenUIEvent {
    data object NavigateUp : CategoriesScreenUIEvent()

    data class DeleteCategory(
        val categoryId: Int,
    ) : CategoriesScreenUIEvent()

    data class NavigateToAddCategoryScreen(
        val transactionType: String,
    ) : CategoriesScreenUIEvent()

    data class NavigateToEditCategoryScreen(
        val categoryId: Int,
    ) : CategoriesScreenUIEvent()

    data class SetDefaultCategoryIdInDataStore(
        val defaultCategoryId: Int,
        val transactionType: TransactionType,
    ) : CategoriesScreenUIEvent()

    data class UpdateSelectedTabIndex(
        val updatedSelectedTabIndex: Int,
    ) : CategoriesScreenUIEvent()
}
