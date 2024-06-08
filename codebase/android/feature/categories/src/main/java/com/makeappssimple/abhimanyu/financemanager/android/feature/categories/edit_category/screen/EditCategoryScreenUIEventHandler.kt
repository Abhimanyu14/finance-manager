package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.viewmodel.EditCategoryScreenViewModel
import kotlinx.collections.immutable.ImmutableList

public class EditCategoryScreenUIEventHandler internal constructor(
    private val viewModel: EditCategoryScreenViewModel,
    private val uiStateAndEvents: EditCategoryScreenUIStateAndEvents,
    private val category: Category?,
    private val validTransactionTypes: ImmutableList<TransactionType>,
) {
    public fun handleUIEvent(
        uiEvent: EditCategoryScreenUIEvent,
    ) {
        when (uiEvent) {
            is EditCategoryScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
                uiStateAndEvents.events.setSearchText("")
            }

            is EditCategoryScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is EditCategoryScreenUIEvent.OnCtaButtonClick -> {
                category?.let { category ->
                    uiStateAndEvents.state.selectedTransactionTypeIndex?.let { selectedTransactionTypeIndex ->
                        viewModel.updateCategory(
                            category = category.copy(
                                emoji = uiStateAndEvents.state.emoji,
                                title = uiStateAndEvents.state.title.text,
                                transactionType = validTransactionTypes[selectedTransactionTypeIndex],
                            ),
                        )
                    }
                }
                Unit
            }

            is EditCategoryScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateAndEvents.events.clearTitle()
            }

            is EditCategoryScreenUIEvent.OnEmojiCircleClick -> {
                uiStateAndEvents.events.setScreenBottomSheetType(
                    EditCategoryScreenBottomSheetType.SelectEmoji
                )
            }

            is EditCategoryScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                viewModel.navigateUp()
            }

            is EditCategoryScreenUIEvent.OnEmojiUpdated -> {
                uiStateAndEvents.events.setEmoji(uiEvent.updatedEmoji)
            }

            is EditCategoryScreenUIEvent.OnEmojiBottomSheetSearchTextUpdated -> {
                uiStateAndEvents.events.setSearchText(uiEvent.updatedSearchText)
            }

            is EditCategoryScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                uiStateAndEvents.events.setSelectedTransactionTypeIndex(uiEvent.updatedIndex)
            }

            is EditCategoryScreenUIEvent.OnTitleUpdated -> {
                uiStateAndEvents.events.setTitle(uiEvent.updatedTitle)
            }
        }
    }
}
