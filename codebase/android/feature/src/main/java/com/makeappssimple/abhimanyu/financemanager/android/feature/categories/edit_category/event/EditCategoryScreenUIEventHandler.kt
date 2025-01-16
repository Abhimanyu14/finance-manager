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
                uiStateEvents.setSearchText("")
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
                uiStateEvents.setScreenBottomSheetType(
                    EditCategoryScreenBottomSheetType.SelectEmoji
                )
            }

            is EditCategoryScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is EditCategoryScreenUIEvent.OnEmojiUpdated -> {
                uiStateEvents.setEmoji(uiEvent.updatedEmoji)
            }

            is EditCategoryScreenUIEvent.OnEmojiBottomSheetSearchTextUpdated -> {
                uiStateEvents.setSearchText(uiEvent.updatedSearchText)
            }

            is EditCategoryScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                uiStateEvents.setSelectedTransactionTypeIndex(uiEvent.updatedIndex)
            }

            is EditCategoryScreenUIEvent.OnTitleUpdated -> {
                uiStateEvents.setTitle(uiEvent.updatedTitle)
            }
        }
    }
}
