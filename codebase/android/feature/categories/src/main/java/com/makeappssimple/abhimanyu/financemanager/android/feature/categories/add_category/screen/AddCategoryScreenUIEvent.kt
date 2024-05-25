package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class AddCategoryScreenUIEvent : ScreenUIEvent {
    public data object OnNavigationBackButtonClick : AddCategoryScreenUIEvent()
    public data object OnBottomSheetDismissed : AddCategoryScreenUIEvent()
    public data object OnClearTitleButtonClick : AddCategoryScreenUIEvent()
    public data object OnCtaButtonClick : AddCategoryScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : AddCategoryScreenUIEvent()
    public data object OnEmojiCircleClick : AddCategoryScreenUIEvent()

    public data class OnEmojiBottomSheetSearchTextUpdated(
        val updatedSearchText: String,
    ) : AddCategoryScreenUIEvent()

    public data class OnEmojiUpdated(
        val updatedEmoji: String,
    ) : AddCategoryScreenUIEvent()

    public data class OnSelectedTransactionTypeIndexUpdated(
        val updatedIndex: Int,
    ) : AddCategoryScreenUIEvent()

    public data class OnTitleUpdated(
        val updatedTitle: TextFieldValue,
    ) : AddCategoryScreenUIEvent()
}
