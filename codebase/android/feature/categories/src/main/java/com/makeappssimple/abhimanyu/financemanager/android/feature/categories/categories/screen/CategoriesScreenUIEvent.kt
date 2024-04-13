package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class CategoriesScreenUIEvent : ScreenUIEvent {
    public data object NavigateUp : CategoriesScreenUIEvent()

    public data class DeleteCategory(
        val categoryId: Int,
    ) : CategoriesScreenUIEvent()

    public data class NavigateToAddCategoryScreen(
        val transactionType: String,
    ) : CategoriesScreenUIEvent()

    public data class NavigateToEditCategoryScreen(
        val categoryId: Int,
    ) : CategoriesScreenUIEvent()

    public data class SetDefaultCategoryIdInDataStore(
        val defaultCategoryId: Int,
        val transactionType: TransactionType,
    ) : CategoriesScreenUIEvent()

    public data class UpdateSelectedTabIndex(
        val updatedSelectedTabIndex: Int,
    ) : CategoriesScreenUIEvent()
}
