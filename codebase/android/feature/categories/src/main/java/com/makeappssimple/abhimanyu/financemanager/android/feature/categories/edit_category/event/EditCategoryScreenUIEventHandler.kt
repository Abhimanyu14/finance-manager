package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.event

import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.bottomsheet.EditCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.state.EditCategoryScreenUIStateAndStateEvents

public class EditCategoryScreenUIEventHandler internal constructor(
    private val uiStateAndStateEvents: EditCategoryScreenUIStateAndStateEvents,
) {
    public fun handleUIEvent(
        uiEvent: EditCategoryScreenUIEvent,
    ) {
        when (uiEvent) {
            is EditCategoryScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
                uiStateAndStateEvents.events.setSearchText("")
            }

            is EditCategoryScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is EditCategoryScreenUIEvent.OnCtaButtonClick -> {
                uiStateAndStateEvents.state.selectedTransactionTypeIndex?.let { _ ->
                    uiStateAndStateEvents.events.updateCategory()
                }
            }

            is EditCategoryScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateAndStateEvents.events.clearTitle()
            }

            is EditCategoryScreenUIEvent.OnEmojiCircleClick -> {
                uiStateAndStateEvents.events.setScreenBottomSheetType(
                    EditCategoryScreenBottomSheetType.SelectEmoji
                )
            }

            is EditCategoryScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateAndStateEvents.events.navigateUp()
            }

            is EditCategoryScreenUIEvent.OnEmojiUpdated -> {
                uiStateAndStateEvents.events.setEmoji(uiEvent.updatedEmoji)
            }

            is EditCategoryScreenUIEvent.OnEmojiBottomSheetSearchTextUpdated -> {
                uiStateAndStateEvents.events.setSearchText(uiEvent.updatedSearchText)
            }

            is EditCategoryScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                uiStateAndStateEvents.events.setSelectedTransactionTypeIndex(uiEvent.updatedIndex)
            }

            is EditCategoryScreenUIEvent.OnTitleUpdated -> {
                uiStateAndStateEvents.events.setTitle(uiEvent.updatedTitle)
            }
        }
    }
}
