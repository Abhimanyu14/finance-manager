package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.event

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
internal sealed class AddCategoryScreenUIEvent : ScreenUIEvent {
    data object OnNavigationBackButtonClick : AddCategoryScreenUIEvent()
    data object OnBottomSheetDismissed : AddCategoryScreenUIEvent()
    data object OnClearTitleButtonClick : AddCategoryScreenUIEvent()
    data object OnCtaButtonClick : AddCategoryScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : AddCategoryScreenUIEvent()
    data object OnEmojiCircleClick : AddCategoryScreenUIEvent()

    data class OnEmojiBottomSheetSearchTextUpdated(
        val updatedSearchText: String,
    ) : AddCategoryScreenUIEvent()

    data class OnEmojiUpdated(
        val updatedEmoji: String,
    ) : AddCategoryScreenUIEvent()

    data class OnSelectedTransactionTypeIndexUpdated(
        val updatedIndex: Int,
    ) : AddCategoryScreenUIEvent()

    data class OnTitleUpdated(
        val updatedTitle: TextFieldValue,
    ) : AddCategoryScreenUIEvent()
}
