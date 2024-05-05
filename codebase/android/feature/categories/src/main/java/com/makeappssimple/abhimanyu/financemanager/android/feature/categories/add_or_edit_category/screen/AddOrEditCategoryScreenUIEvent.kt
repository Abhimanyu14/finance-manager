package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class AddOrEditCategoryScreenUIEvent : ScreenUIEvent {
    public data object ClearTitle : AddOrEditCategoryScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : AddOrEditCategoryScreenUIEvent()
    public data object OnCtaButtonClick : AddOrEditCategoryScreenUIEvent()

    public data class UpdateEmoji(
        val updatedEmoji: String,
    ) : AddOrEditCategoryScreenUIEvent()

    public data class UpdateSearchText(
        val updatedSearchText: String,
    ) : AddOrEditCategoryScreenUIEvent()

    public data class UpdateSelectedTransactionTypeIndex(
        val updatedIndex: Int,
    ) : AddOrEditCategoryScreenUIEvent()

    public data class UpdateTitle(
        val updatedTitle: TextFieldValue,
    ) : AddOrEditCategoryScreenUIEvent()
}
