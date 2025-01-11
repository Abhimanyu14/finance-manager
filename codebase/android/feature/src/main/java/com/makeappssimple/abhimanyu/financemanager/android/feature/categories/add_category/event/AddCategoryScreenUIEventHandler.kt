package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.event

import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.bottomsheet.AddCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state.AddCategoryScreenUIStateEvents

internal class AddCategoryScreenUIEventHandler internal constructor(
    private val uiStateEvents: AddCategoryScreenUIStateEvents,
) {
    fun handleUIEvent(
        uiEvent: AddCategoryScreenUIEvent,
    ) {
        when (uiEvent) {
            is AddCategoryScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.clearSearchText()
            }

            is AddCategoryScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is AddCategoryScreenUIEvent.OnCtaButtonClick -> {
                uiStateEvents.insertCategory()
            }

            is AddCategoryScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateEvents.clearTitle()
            }

            is AddCategoryScreenUIEvent.OnEmojiCircleClick -> {
                uiStateEvents.setScreenBottomSheetType(
                    AddCategoryScreenBottomSheetType.SelectEmoji
                )
            }

            is AddCategoryScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is AddCategoryScreenUIEvent.OnEmojiUpdated -> {
                uiStateEvents.setEmoji(uiEvent.updatedEmoji)
            }

            is AddCategoryScreenUIEvent.OnEmojiBottomSheetSearchTextUpdated -> {
                uiStateEvents.setSearchText(uiEvent.updatedSearchText)
            }

            is AddCategoryScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                uiStateEvents.setSelectedTransactionTypeIndex(uiEvent.updatedIndex)
            }

            is AddCategoryScreenUIEvent.OnTitleUpdated -> {
                uiStateEvents.setTitle(uiEvent.updatedTitle)
            }
        }
    }
}
