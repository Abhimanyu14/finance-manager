package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.event

import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.bottomsheet.EditTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.EditTransactionScreenUIStateAndStateEvents

internal class EditTransactionScreenUIEventHandler internal constructor(
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
                uiStateAndStateEvents.events.updateTransaction()
            }

            is EditTransactionScreenUIEvent.OnClearAmountButtonClick -> {
                uiStateAndStateEvents.events.clearAmount()
            }

            is EditTransactionScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateAndStateEvents.events.clearTitle()
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
                uiStateAndStateEvents.events.navigateUp()
            }

            is EditTransactionScreenUIEvent.OnAccountFromUpdated -> {
                uiStateAndStateEvents.events.setAccountFrom(uiEvent.updatedAccountFrom)
            }

            is EditTransactionScreenUIEvent.OnAccountToUpdated -> {
                uiStateAndStateEvents.events.setAccountTo(uiEvent.updatedAccountTo)
            }

            is EditTransactionScreenUIEvent.OnAmountUpdated -> {
                uiStateAndStateEvents.events.setAmount(uiEvent.updatedAmount)
            }

            is EditTransactionScreenUIEvent.OnCategoryUpdated -> {
                uiStateAndStateEvents.events.setCategory(uiEvent.updatedCategory)
            }

            is EditTransactionScreenUIEvent.OnSelectedTransactionForIndexUpdated -> {
                uiStateAndStateEvents.events.setSelectedTransactionForIndex(uiEvent.updatedSelectedTransactionForIndex)
            }

            is EditTransactionScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                uiStateAndStateEvents.events.setSelectedTransactionTypeIndex(uiEvent.updatedSelectedTransactionTypeIndex)
            }

            is EditTransactionScreenUIEvent.OnTransactionTimePickerDismissed -> {
                uiStateAndStateEvents.events.setIsTransactionTimePickerDialogVisible(false)
            }

            is EditTransactionScreenUIEvent.OnTransactionDatePickerDismissed -> {
                uiStateAndStateEvents.events.setIsTransactionDatePickerDialogVisible(false)
            }

            is EditTransactionScreenUIEvent.OnTitleUpdated -> {
                uiStateAndStateEvents.events.setTitle(uiEvent.updatedTitle)
            }

            is EditTransactionScreenUIEvent.OnTransactionDateUpdated -> {
                uiStateAndStateEvents.events.setTransactionDate(uiEvent.updatedTransactionDate)
                uiStateAndStateEvents.events.setIsTransactionDatePickerDialogVisible(false)
            }

            is EditTransactionScreenUIEvent.OnTransactionTimeUpdated -> {
                uiStateAndStateEvents.events.setTransactionTime(uiEvent.updatedTransactionTime)
                uiStateAndStateEvents.events.setIsTransactionTimePickerDialogVisible(false)
            }

            is EditTransactionScreenUIEvent.OnSnackbarDismissed -> {
                uiStateAndStateEvents.events.resetScreenSnackbarType()
            }
        }
    }
}
