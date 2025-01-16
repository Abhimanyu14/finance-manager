package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.event

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.bottomsheet.EditTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.EditTransactionScreenUIStateEvents

internal class EditTransactionScreenUIEventHandler internal constructor(
    private val uiStateEvents: EditTransactionScreenUIStateEvents,
) : ScreenUIEventHandler<EditTransactionScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: EditTransactionScreenUIEvent,
    ) {
        when (uiEvent) {
            is EditTransactionScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is EditTransactionScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is EditTransactionScreenUIEvent.OnCtaButtonClick -> {
                uiStateEvents.updateTransaction()
            }

            is EditTransactionScreenUIEvent.OnClearAmountButtonClick -> {
                uiStateEvents.clearAmount()
            }

            is EditTransactionScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateEvents.clearTitle()
            }

            is EditTransactionScreenUIEvent.OnAccountFromTextFieldClick -> {
                uiStateEvents.setScreenBottomSheetType(
                    EditTransactionScreenBottomSheetType.SelectAccountFrom
                )
            }

            is EditTransactionScreenUIEvent.OnAccountToTextFieldClick -> {
                uiStateEvents.setScreenBottomSheetType(
                    EditTransactionScreenBottomSheetType.SelectAccountTo
                )
            }

            is EditTransactionScreenUIEvent.OnTransactionTimeTextFieldClick -> {
                uiStateEvents.setIsTransactionTimePickerDialogVisible(true)
            }

            is EditTransactionScreenUIEvent.OnTransactionDateTextFieldClick -> {
                uiStateEvents.setIsTransactionDatePickerDialogVisible(true)
            }

            is EditTransactionScreenUIEvent.OnCategoryTextFieldClick -> {
                uiStateEvents.setScreenBottomSheetType(
                    EditTransactionScreenBottomSheetType.SelectCategory
                )
            }

            is EditTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is EditTransactionScreenUIEvent.OnAccountFromUpdated -> {
                uiStateEvents.setAccountFrom(uiEvent.updatedAccountFrom)
            }

            is EditTransactionScreenUIEvent.OnAccountToUpdated -> {
                uiStateEvents.setAccountTo(uiEvent.updatedAccountTo)
            }

            is EditTransactionScreenUIEvent.OnAmountUpdated -> {
                uiStateEvents.setAmount(uiEvent.updatedAmount)
            }

            is EditTransactionScreenUIEvent.OnCategoryUpdated -> {
                uiStateEvents.setCategory(uiEvent.updatedCategory)
            }

            is EditTransactionScreenUIEvent.OnSelectedTransactionForIndexUpdated -> {
                uiStateEvents.setSelectedTransactionForIndex(uiEvent.updatedSelectedTransactionForIndex)
            }

            is EditTransactionScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                uiStateEvents.setSelectedTransactionTypeIndex(uiEvent.updatedSelectedTransactionTypeIndex)
            }

            is EditTransactionScreenUIEvent.OnTransactionTimePickerDismissed -> {
                uiStateEvents.setIsTransactionTimePickerDialogVisible(false)
            }

            is EditTransactionScreenUIEvent.OnTransactionDatePickerDismissed -> {
                uiStateEvents.setIsTransactionDatePickerDialogVisible(false)
            }

            is EditTransactionScreenUIEvent.OnTitleUpdated -> {
                uiStateEvents.setTitle(uiEvent.updatedTitle)
            }

            is EditTransactionScreenUIEvent.OnTransactionDateUpdated -> {
                uiStateEvents.setTransactionDate(uiEvent.updatedTransactionDate)
                uiStateEvents.setIsTransactionDatePickerDialogVisible(false)
            }

            is EditTransactionScreenUIEvent.OnTransactionTimeUpdated -> {
                uiStateEvents.setTransactionTime(uiEvent.updatedTransactionTime)
                uiStateEvents.setIsTransactionTimePickerDialogVisible(false)
            }

            is EditTransactionScreenUIEvent.OnSnackbarDismissed -> {
                uiStateEvents.resetScreenSnackbarType()
            }
        }
    }
}
