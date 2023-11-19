package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
sealed class AddOrEditCategoryScreenUIEvent : ScreenUIEvent {
    data object ClearTitle : AddOrEditCategoryScreenUIEvent()
    data object NavigateUp : AddOrEditCategoryScreenUIEvent()
    data object OnCtaButtonClick : AddOrEditCategoryScreenUIEvent()

    data class UpdateEmoji(
        val updatedEmoji: String,
    ) : AddOrEditCategoryScreenUIEvent()

    data class UpdateSearchText(
        val updatedSearchText: String,
    ) : AddOrEditCategoryScreenUIEvent()

    data class UpdateSelectedTransactionTypeIndex(
        val updatedIndex: Int,
    ) : AddOrEditCategoryScreenUIEvent()

    data class UpdateTitle(
        val updatedTitle: TextFieldValue,
    ) : AddOrEditCategoryScreenUIEvent()
}
