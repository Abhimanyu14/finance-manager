package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.event

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.bottomsheet.EditCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state.EditCategoryScreenUIStateEvents

internal class EditCategoryScreenUIEventHandler internal constructor(
    private val uiStateEvents: EditCategoryScreenUIStateEvents,
) : ScreenUIEventHandler<EditCategoryScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: EditCategoryScreenUIEvent,
    ) {
        when (uiEvent) {
            is EditCategoryScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.updateSearchText("")
            }

            is EditCategoryScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is EditCategoryScreenUIEvent.OnCtaButtonClick -> {
                uiStateEvents.updateCategory()
            }

            is EditCategoryScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateEvents.clearTitle()
            }

            is EditCategoryScreenUIEvent.OnEmojiCircleClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    EditCategoryScreenBottomSheetType.SelectEmoji
                )
            }

            is EditCategoryScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is EditCategoryScreenUIEvent.OnEmojiUpdated -> {
                uiStateEvents.updateEmoji(uiEvent.updatedEmoji)
            }

            is EditCategoryScreenUIEvent.OnEmojiBottomSheetSearchTextUpdated -> {
                uiStateEvents.updateSearchText(uiEvent.updatedSearchText)
            }

            is EditCategoryScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                uiStateEvents.updateSelectedTransactionTypeIndex(uiEvent.updatedIndex)
            }

            is EditCategoryScreenUIEvent.OnTitleUpdated -> {
                uiStateEvents.updateTitle(uiEvent.updatedTitle)
            }
        }
    }
}
