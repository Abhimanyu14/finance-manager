package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.event

import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.bottomsheet.AddTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state.AddTransactionScreenUIStateEvents

internal class AddTransactionScreenUIEventHandler(
    private val uiStateEvents: AddTransactionScreenUIStateEvents,
) {
    fun handleUIEvent(
        uiEvent: AddTransactionScreenUIEvent,
    ) {
        when (uiEvent) {
            is AddTransactionScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is AddTransactionScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is AddTransactionScreenUIEvent.OnCtaButtonClick -> {
                uiStateEvents.insertTransaction()
            }

            is AddTransactionScreenUIEvent.OnClearAmountButtonClick -> {
                uiStateEvents.clearAmount()
            }

            is AddTransactionScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateEvents.clearTitle()
            }

            is AddTransactionScreenUIEvent.OnAccountFromTextFieldClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    AddTransactionScreenBottomSheetType.SelectAccountFrom
                )
            }

            is AddTransactionScreenUIEvent.OnAccountToTextFieldClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    AddTransactionScreenBottomSheetType.SelectAccountTo
                )
            }

            is AddTransactionScreenUIEvent.OnTransactionTimeTextFieldClick -> {
                uiStateEvents.updateIsTransactionTimePickerDialogVisible(true)
            }

            is AddTransactionScreenUIEvent.OnTransactionDateTextFieldClick -> {
                uiStateEvents.updateIsTransactionDatePickerDialogVisible(true)
            }

            is AddTransactionScreenUIEvent.OnCategoryTextFieldClick -> {
                uiStateEvents.updateScreenBottomSheetType(
                    AddTransactionScreenBottomSheetType.SelectCategory
                )
            }

            is AddTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is AddTransactionScreenUIEvent.OnAccountFromUpdated -> {
                uiStateEvents.updateAccountFrom(uiEvent.updatedAccountFrom)
            }

            is AddTransactionScreenUIEvent.OnAccountToUpdated -> {
                uiStateEvents.updateAccountTo(uiEvent.updatedAccountTo)
            }

            is AddTransactionScreenUIEvent.OnAmountUpdated -> {
                uiStateEvents.updateAmount(uiEvent.updatedAmount)
            }

            is AddTransactionScreenUIEvent.OnCategoryUpdated -> {
                uiStateEvents.updateCategory(uiEvent.updatedCategory)
            }

            is AddTransactionScreenUIEvent.OnSelectedTransactionForIndexUpdated -> {
                uiStateEvents.updateSelectedTransactionForIndex(uiEvent.updatedSelectedTransactionForIndex)
            }

            is AddTransactionScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                uiStateEvents.updateSelectedTransactionTypeIndex(uiEvent.updatedSelectedTransactionTypeIndex)
            }

            is AddTransactionScreenUIEvent.OnTransactionTimePickerDismissed -> {
                uiStateEvents.updateIsTransactionTimePickerDialogVisible(false)
            }

            is AddTransactionScreenUIEvent.OnTransactionDatePickerDismissed -> {
                uiStateEvents.updateIsTransactionDatePickerDialogVisible(false)
            }

            is AddTransactionScreenUIEvent.OnTitleUpdated -> {
                uiStateEvents.updateTitle(uiEvent.updatedTitle)
            }

            is AddTransactionScreenUIEvent.OnTransactionDateUpdated -> {
                uiStateEvents.updateTransactionDate(uiEvent.updatedTransactionDate)
                uiStateEvents.updateIsTransactionDatePickerDialogVisible(false)
            }

            is AddTransactionScreenUIEvent.OnTransactionTimeUpdated -> {
                uiStateEvents.updateTransactionTime(uiEvent.updatedTransactionTime)
                uiStateEvents.updateIsTransactionTimePickerDialogVisible(false)
            }

            is AddTransactionScreenUIEvent.OnSnackbarDismissed -> {
                uiStateEvents.resetScreenSnackbarType()
            }
        }
    }
}
