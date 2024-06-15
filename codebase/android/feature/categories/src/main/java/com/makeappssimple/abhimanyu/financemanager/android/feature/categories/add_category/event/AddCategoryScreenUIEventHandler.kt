package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.event

import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.bottomsheet.AddCategoryScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.state.AddCategoryScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.viewmodel.AddCategoryScreenViewModel
import kotlinx.collections.immutable.ImmutableList

public class AddCategoryScreenUIEventHandler internal constructor(
    private val viewModel: AddCategoryScreenViewModel,
    private val uiStateAndStateEvents: AddCategoryScreenUIStateAndStateEvents,
    private val validTransactionTypes: ImmutableList<TransactionType>,
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
                uiStateAndStateEvents.state.selectedTransactionTypeIndex?.let { selectedTransactionTypeIndex ->
                    val transactionType = validTransactionTypes[selectedTransactionTypeIndex]
                    viewModel.insertCategory(
                        category = Category(
                            emoji = uiStateAndStateEvents.state.emoji,
                            title = uiStateAndStateEvents.state.title.text,
                            transactionType = transactionType,
                        ),
                    )
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
                viewModel.navigateUp()
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
