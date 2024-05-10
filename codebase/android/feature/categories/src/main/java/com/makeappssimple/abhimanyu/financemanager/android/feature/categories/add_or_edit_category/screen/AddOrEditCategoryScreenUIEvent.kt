package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class AddOrEditCategoryScreenUIEvent : ScreenUIEvent {
    public data object OnNavigationBackButtonClick : AddOrEditCategoryScreenUIEvent()
    public data object OnBottomSheetDismissed : AddOrEditCategoryScreenUIEvent()
    public data object OnClearTitleButtonClick : AddOrEditCategoryScreenUIEvent()
    public data object OnCtaButtonClick : AddOrEditCategoryScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : AddOrEditCategoryScreenUIEvent()

    public data class OnEmojiBottomSheetSearchTextUpdated(
        val updatedSearchText: String,
    ) : AddOrEditCategoryScreenUIEvent()

    public data class OnEmojiUpdated(
        val updatedEmoji: String,
    ) : AddOrEditCategoryScreenUIEvent()

    public data class OnSelectedTransactionTypeIndexUpdated(
        val updatedIndex: Int,
    ) : AddOrEditCategoryScreenUIEvent()

    public data class OnTitleUpdated(
        val updatedTitle: TextFieldValue,
    ) : AddOrEditCategoryScreenUIEvent()
}
