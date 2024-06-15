package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.event

import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.bottomsheet.AddCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state.AddCategoryScreenUIStateAndStateEvents

public class AddCategoryScreenUIEventHandler internal constructor(
    private val uiStateAndStateEvents: AddCategoryScreenUIStateAndStateEvents,
) {
    public fun handleUIEvent(
        uiEvent: AddCategoryScreenUIEvent,
    ) {
        when (uiEvent) {
            is AddCategoryScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
                uiStateAndStateEvents.events.setSearchText("")
            }

            is AddCategoryScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is AddCategoryScreenUIEvent.OnCtaButtonClick -> {
                uiStateAndStateEvents.state.selectedTransactionTypeIndex?.let { _ ->
                    uiStateAndStateEvents.events.insertCategory()
                }
            }

            is AddCategoryScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateAndStateEvents.events.clearTitle()
            }

            is AddCategoryScreenUIEvent.OnEmojiCircleClick -> {
                uiStateAndStateEvents.events.setScreenBottomSheetType(
                    AddCategoryScreenBottomSheetType.SelectEmoji
                )
            }

            is AddCategoryScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateAndStateEvents.events.navigateUp()
            }

            is AddCategoryScreenUIEvent.OnEmojiUpdated -> {
                uiStateAndStateEvents.events.setEmoji(uiEvent.updatedEmoji)
            }

            is AddCategoryScreenUIEvent.OnEmojiBottomSheetSearchTextUpdated -> {
                uiStateAndStateEvents.events.setSearchText(uiEvent.updatedSearchText)
            }

            is AddCategoryScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                uiStateAndStateEvents.events.setSelectedTransactionTypeIndex(uiEvent.updatedIndex)
            }

            is AddCategoryScreenUIEvent.OnTitleUpdated -> {
                uiStateAndStateEvents.events.setTitle(uiEvent.updatedTitle)
            }
        }
    }
}
