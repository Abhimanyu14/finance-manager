package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.viewmodel.AddCategoryScreenViewModel
import kotlinx.collections.immutable.ImmutableList

public class AddCategoryScreenUIEventHandler internal constructor(
    private val viewModel: AddCategoryScreenViewModel,
    private val uiStateAndEvents: AddCategoryScreenUIStateAndEvents,
    private val validTransactionTypes: ImmutableList<TransactionType>,
) {
    public fun handleUIEvent(
        uiEvent: AddCategoryScreenUIEvent,
    ) {
        when (uiEvent) {
            is AddCategoryScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
                uiStateAndEvents.events.setSearchText("")
            }

            is AddCategoryScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is AddCategoryScreenUIEvent.OnCtaButtonClick -> {
                uiStateAndEvents.state.selectedTransactionTypeIndex?.let { selectedTransactionTypeIndex ->
                    val transactionType = validTransactionTypes[selectedTransactionTypeIndex]
                    viewModel.insertCategory(
                        category = Category(
                            emoji = uiStateAndEvents.state.emoji,
                            title = uiStateAndEvents.state.title.text,
                            transactionType = transactionType,
                        ),
                    )
                }
            }

            is AddCategoryScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateAndEvents.events.clearTitle()
            }

            is AddCategoryScreenUIEvent.OnEmojiCircleClick -> {
                uiStateAndEvents.events.setScreenBottomSheetType(
                    AddCategoryScreenBottomSheetType.SelectEmoji
                )
            }

            is AddCategoryScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                viewModel.navigateUp()
            }

            is AddCategoryScreenUIEvent.OnEmojiUpdated -> {
                uiStateAndEvents.events.setEmoji(uiEvent.updatedEmoji)
            }

            is AddCategoryScreenUIEvent.OnEmojiBottomSheetSearchTextUpdated -> {
                uiStateAndEvents.events.setSearchText(uiEvent.updatedSearchText)
            }

            is AddCategoryScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                uiStateAndEvents.events.setSelectedTransactionTypeIndex(uiEvent.updatedIndex)
            }

            is AddCategoryScreenUIEvent.OnTitleUpdated -> {
                uiStateAndEvents.events.setTitle(uiEvent.updatedTitle)
            }
        }
    }
}
