package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class CategoriesScreenUIEvent : ScreenUIEvent {
    public data object OnBottomSheetDismissed : CategoriesScreenUIEvent()
    public data object OnNavigationBackButtonClick : CategoriesScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : CategoriesScreenUIEvent()

    public data class OnFloatingActionButtonClick(
        val transactionType: String,
    ) : CategoriesScreenUIEvent()

    public data class OnSelectedTabIndexUpdated(
        val updatedSelectedTabIndex: Int,
    ) : CategoriesScreenUIEvent()

    public data class OnCategoriesGridItemClick(
        val isDeleteVisible: Boolean,
        val isEditVisible: Boolean,
        val isSetAsDefaultVisible: Boolean,
        val categoryId: Int?,
    ) : CategoriesScreenUIEvent()

    public sealed class OnCategoriesSetAsDefaultConfirmationBottomSheet {
        public data object NegativeButtonClick : CategoriesScreenUIEvent()
        public data object PositiveButtonClick : CategoriesScreenUIEvent()
    }

    public sealed class OnCategoryMenuBottomSheet {
        public data class DeleteButtonClick(
            val categoryId: Int,
        ) : CategoriesScreenUIEvent()

        public data class EditButtonClick(
            val categoryId: Int,
        ) : CategoriesScreenUIEvent()

        public data class SetAsDefaultButtonClick(
            val categoryId: Int,
        ) : CategoriesScreenUIEvent()
    }

    public sealed class OnCategoriesDeleteConfirmationBottomSheet {
        public data object NegativeButtonClick : CategoriesScreenUIEvent()
        public data object PositiveButtonClick : CategoriesScreenUIEvent()
    }
}
