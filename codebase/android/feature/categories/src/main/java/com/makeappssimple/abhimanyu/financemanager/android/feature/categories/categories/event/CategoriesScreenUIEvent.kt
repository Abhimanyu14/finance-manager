package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.event

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
internal sealed class CategoriesScreenUIEvent : ScreenUIEvent {
    data object OnNavigationBackButtonClick : CategoriesScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : CategoriesScreenUIEvent()

    data class OnFloatingActionButtonClick(
        val transactionType: String,
    ) : CategoriesScreenUIEvent()

    data class OnSelectedTabIndexUpdated(
        val updatedSelectedTabIndex: Int,
    ) : CategoriesScreenUIEvent()

    data class OnCategoriesGridItemClick(
        val isDeleteVisible: Boolean,
        val isEditVisible: Boolean,
        val isSetAsDefaultVisible: Boolean,
        val categoryId: Int?,
    ) : CategoriesScreenUIEvent()

    sealed class OnCategoriesSetAsDefaultConfirmationBottomSheet {
        data object NegativeButtonClick : CategoriesScreenUIEvent()
        data object PositiveButtonClick : CategoriesScreenUIEvent()
    }

    sealed class OnCategoryMenuBottomSheet {
        data class DeleteButtonClick(
            val categoryId: Int,
        ) : CategoriesScreenUIEvent()

        data class EditButtonClick(
            val categoryId: Int,
        ) : CategoriesScreenUIEvent()

        data class SetAsDefaultButtonClick(
            val categoryId: Int,
        ) : CategoriesScreenUIEvent()
    }

    sealed class OnCategoriesDeleteConfirmationBottomSheet {
        data object NegativeButtonClick : CategoriesScreenUIEvent()
        data object PositiveButtonClick : CategoriesScreenUIEvent()
    }
}
