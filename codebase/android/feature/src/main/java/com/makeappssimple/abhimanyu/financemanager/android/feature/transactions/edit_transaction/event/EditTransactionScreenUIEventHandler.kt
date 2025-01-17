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
                uiStateEvents.updateScreenBottomSheetType(
                    EditTransactionScreenBottomSheetType.SelectAccountFrom
                )
            }

            is EditTransactionScreenUIEvent.OnAccountToTextFieldClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    EditTransactionScreenBottomSheetType.SelectAccountTo
                )
            }

            is EditTransactionScreenUIEvent.OnTransactionTimeTextFieldClick -> {
                uiStateEvents.updateIsTransactionTimePickerDialogVisible(true)
            }

            is EditTransactionScreenUIEvent.OnTransactionDateTextFieldClick -> {
                uiStateEvents.updateIsTransactionDatePickerDialogVisible(true)
            }

            is EditTransactionScreenUIEvent.OnCategoryTextFieldClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    EditTransactionScreenBottomSheetType.SelectCategory
                )
            }

            is EditTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is EditTransactionScreenUIEvent.OnAccountFromUpdated -> {
                uiStateEvents.updateAccountFrom(uiEvent.updatedAccountFrom)
            }

            is EditTransactionScreenUIEvent.OnAccountToUpdated -> {
                uiStateEvents.updateAccountTo(uiEvent.updatedAccountTo)
            }

            is EditTransactionScreenUIEvent.OnAmountUpdated -> {
                uiStateEvents.updateAmount(uiEvent.updatedAmount)
            }

            is EditTransactionScreenUIEvent.OnCategoryUpdated -> {
                uiStateEvents.updateCategory(uiEvent.updatedCategory)
            }

            is EditTransactionScreenUIEvent.OnSelectedTransactionForIndexUpdated -> {
                uiStateEvents.updateSelectedTransactionForIndex(uiEvent.updatedSelectedTransactionForIndex)
            }

            is EditTransactionScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                uiStateEvents.updateSelectedTransactionTypeIndex(uiEvent.updatedSelectedTransactionTypeIndex)
            }

            is EditTransactionScreenUIEvent.OnTransactionTimePickerDismissed -> {
                uiStateEvents.updateIsTransactionTimePickerDialogVisible(false)
            }

            is EditTransactionScreenUIEvent.OnTransactionDatePickerDismissed -> {
                uiStateEvents.updateIsTransactionDatePickerDialogVisible(false)
            }

            is EditTransactionScreenUIEvent.OnTitleUpdated -> {
                uiStateEvents.updateTitle(uiEvent.updatedTitle)
            }

            is EditTransactionScreenUIEvent.OnTransactionDateUpdated -> {
                uiStateEvents.updateTransactionDate(uiEvent.updatedTransactionDate)
                uiStateEvents.updateIsTransactionDatePickerDialogVisible(false)
            }

            is EditTransactionScreenUIEvent.OnTransactionTimeUpdated -> {
                uiStateEvents.updateTransactionTime(uiEvent.updatedTransactionTime)
                uiStateEvents.updateIsTransactionTimePickerDialogVisible(false)
            }

            is EditTransactionScreenUIEvent.OnSnackbarDismissed -> {
                uiStateEvents.resetScreenSnackbarType()
            }
        }
    }
}
