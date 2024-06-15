package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.event

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class EditCategoryScreenUIEvent : ScreenUIEvent {
    public data object OnNavigationBackButtonClick : EditCategoryScreenUIEvent()
    public data object OnBottomSheetDismissed : EditCategoryScreenUIEvent()
    public data object OnClearTitleButtonClick : EditCategoryScreenUIEvent()
    public data object OnCtaButtonClick : EditCategoryScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : EditCategoryScreenUIEvent()
    public data object OnEmojiCircleClick : EditCategoryScreenUIEvent()

    public data class OnEmojiBottomSheetSearchTextUpdated(
        val updatedSearchText: String,
    ) : EditCategoryScreenUIEvent()

    public data class OnEmojiUpdated(
        val updatedEmoji: String,
    ) : EditCategoryScreenUIEvent()

    public data class OnSelectedTransactionTypeIndexUpdated(
        val updatedIndex: Int,
    ) : EditCategoryScreenUIEvent()

    public data class OnTitleUpdated(
        val updatedTitle: TextFieldValue,
    ) : EditCategoryScreenUIEvent()
}
