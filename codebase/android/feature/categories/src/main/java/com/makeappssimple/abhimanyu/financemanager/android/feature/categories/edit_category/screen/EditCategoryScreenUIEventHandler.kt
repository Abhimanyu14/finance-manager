package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.viewmodel.EditCategoryScreenViewModel
import kotlinx.collections.immutable.ImmutableList

public class EditCategoryScreenUIEventHandler internal constructor(
    private val viewModel: EditCategoryScreenViewModel,
    private val uiStateAndStateEvents: EditCategoryScreenUIStateAndStateEvents,
    private val category: Category?,
    private val validTransactionTypes: ImmutableList<TransactionType>,
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
                category?.let { category ->
                    uiStateAndStateEvents.state.selectedTransactionTypeIndex?.let { selectedTransactionTypeIndex ->
                        viewModel.updateCategory(
                            category = category.copy(
                                emoji = uiStateAndStateEvents.state.emoji,
                                title = uiStateAndStateEvents.state.title.text,
                                transactionType = validTransactionTypes[selectedTransactionTypeIndex],
                            ),
                        )
                    }
                }
                Unit
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
                viewModel.navigateUp()
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
