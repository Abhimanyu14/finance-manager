package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class CategoriesScreenUIEvent : ScreenUIEvent {
    public data object OnTopAppBarNavigationButtonClick : CategoriesScreenUIEvent()

    public data class OnFloatingActionButtonClick(
        val transactionType: String,
    ) : CategoriesScreenUIEvent()

    public data class OnSelectedTabIndexUpdated(
        val updatedSelectedTabIndex: Int,
    ) : CategoriesScreenUIEvent()

    public sealed class CategoriesDeleteConfirmationBottomSheet {
        public data class DeleteButtonClick(
            val categoryId: Int,
        ) : CategoriesScreenUIEvent()

        public data class EditButtonClick(
            val categoryId: Int,
        ) : CategoriesScreenUIEvent()

        public data class SetAsDefaultCategoryButtonClick(
            val defaultCategoryId: Int,
            val transactionType: TransactionType,
        ) : CategoriesScreenUIEvent()
    }
}
