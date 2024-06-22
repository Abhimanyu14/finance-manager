package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.event

import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.bottomsheet.EditTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.EditTransactionScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel.EditTransactionScreenViewModel

internal class EditTransactionScreenUIEventHandler internal constructor(
    private val viewModel: EditTransactionScreenViewModel,
    private val uiStateAndStateEvents: EditTransactionScreenUIStateAndStateEvents,
) {
    fun handleUIEvent(
        uiEvent: EditTransactionScreenUIEvent,
    ) {
        when (uiEvent) {
            is EditTransactionScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is EditTransactionScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is EditTransactionScreenUIEvent.OnCtaButtonClick -> {
                viewModel.updateTransaction()
            }

            is EditTransactionScreenUIEvent.OnClearAmountButtonClick -> {
                viewModel.clearAmount()
            }

            is EditTransactionScreenUIEvent.OnClearDescriptionButtonClick -> {
                viewModel.clearDescription()
            }

            is EditTransactionScreenUIEvent.OnClearTitleButtonClick -> {
                viewModel.clearTitle()
            }

            is EditTransactionScreenUIEvent.OnAccountFromTextFieldClick -> {
                uiStateAndStateEvents.events.setScreenBottomSheetType(
                    EditTransactionScreenBottomSheetType.SelectAccountFrom
                )
            }

            is EditTransactionScreenUIEvent.OnAccountToTextFieldClick -> {
                uiStateAndStateEvents.events.setScreenBottomSheetType(
                    EditTransactionScreenBottomSheetType.SelectAccountTo
                )
            }

            is EditTransactionScreenUIEvent.OnTransactionTimeTextFieldClick -> {
                uiStateAndStateEvents.events.setIsTransactionTimePickerDialogVisible(true)
            }

            is EditTransactionScreenUIEvent.OnTransactionDateTextFieldClick -> {
                uiStateAndStateEvents.events.setIsTransactionDatePickerDialogVisible(true)
            }

            is EditTransactionScreenUIEvent.OnCategoryTextFieldClick -> {
                uiStateAndStateEvents.events.setScreenBottomSheetType(
                    EditTransactionScreenBottomSheetType.SelectCategory
                )
            }

            is EditTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                viewModel.navigateUp()
            }

            is EditTransactionScreenUIEvent.OnAccountFromUpdated -> {
                viewModel.updateAccountFrom(
                    updatedAccountFrom = uiEvent.updatedAccountFrom,
                )
            }

            is EditTransactionScreenUIEvent.OnAccountToUpdated -> {
                viewModel.updateAccountTo(
                    updatedAccountTo = uiEvent.updatedAccountTo,
                )
            }

            is EditTransactionScreenUIEvent.OnAmountUpdated -> {
                viewModel.updateAmount(
                    updatedAmount = uiEvent.updatedAmount,
                )
            }

            is EditTransactionScreenUIEvent.OnCategoryUpdated -> {
                viewModel.updateCategory(
                    updatedCategory = uiEvent.updatedCategory,
                )
            }

            is EditTransactionScreenUIEvent.OnDescriptionUpdated -> {
                viewModel.updateDescription(
                    updatedDescription = uiEvent.updatedDescription,
                )
            }

            is EditTransactionScreenUIEvent.OnSelectedTransactionForIndexUpdated -> {
                viewModel.updateSelectedTransactionForIndex(
                    updatedSelectedTransactionForIndex = uiEvent.updatedSelectedTransactionForIndex,
                )
            }

            is EditTransactionScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                viewModel.updateSelectedTransactionTypeIndex(
                    updatedSelectedTransactionTypeIndex = uiEvent.updatedSelectedTransactionTypeIndex,
                )
            }

            is EditTransactionScreenUIEvent.OnTransactionTimePickerDismissed -> {
                uiStateAndStateEvents.events.setIsTransactionTimePickerDialogVisible(false)
            }

            is EditTransactionScreenUIEvent.OnTransactionDatePickerDismissed -> {
                uiStateAndStateEvents.events.setIsTransactionDatePickerDialogVisible(false)
            }

            is EditTransactionScreenUIEvent.OnTitleUpdated -> {
                viewModel.updateTitle(
                    updatedTitle = uiEvent.updatedTitle,
                )
            }

            is EditTransactionScreenUIEvent.OnTransactionDateUpdated -> {
                viewModel.updateTransactionDate(
                    updatedTransactionDate = uiEvent.updatedTransactionDate,
                )
                uiStateAndStateEvents.events.setIsTransactionDatePickerDialogVisible(false)
            }

            is EditTransactionScreenUIEvent.OnTransactionTimeUpdated -> {
                viewModel.updateTransactionTime(
                    updatedTransactionTime = uiEvent.updatedTransactionTime,
                )
                uiStateAndStateEvents.events.setIsTransactionTimePickerDialogVisible(false)
            }
        }
    }
}
