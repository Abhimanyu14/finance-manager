package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.event

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
internal sealed class EditCategoryScreenUIEvent : ScreenUIEvent {
    data object OnNavigationBackButtonClick : EditCategoryScreenUIEvent()
    data object OnBottomSheetDismissed : EditCategoryScreenUIEvent()
    data object OnClearTitleButtonClick : EditCategoryScreenUIEvent()
    data object OnCtaButtonClick : EditCategoryScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : EditCategoryScreenUIEvent()
    data object OnEmojiCircleClick : EditCategoryScreenUIEvent()

    data class OnEmojiBottomSheetSearchTextUpdated(
        val updatedSearchText: String,
    ) : EditCategoryScreenUIEvent()

    data class OnEmojiUpdated(
        val updatedEmoji: String,
    ) : EditCategoryScreenUIEvent()

    data class OnSelectedTransactionTypeIndexUpdated(
        val updatedIndex: Int,
    ) : EditCategoryScreenUIEvent()

    data class OnTitleUpdated(
        val updatedTitle: TextFieldValue,
    ) : EditCategoryScreenUIEvent()
}
