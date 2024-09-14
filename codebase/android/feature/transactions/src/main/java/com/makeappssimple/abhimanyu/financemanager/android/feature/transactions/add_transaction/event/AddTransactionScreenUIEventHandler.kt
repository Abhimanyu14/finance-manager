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
                uiStateEvents.setScreenBottomSheetType(
                    AddTransactionScreenBottomSheetType.SelectAccountFrom
                )
            }

            is AddTransactionScreenUIEvent.OnAccountToTextFieldClick -> {
                uiStateEvents.setScreenBottomSheetType(
                    AddTransactionScreenBottomSheetType.SelectAccountTo
                )
            }

            is AddTransactionScreenUIEvent.OnTransactionTimeTextFieldClick -> {
                uiStateEvents.setIsTransactionTimePickerDialogVisible(true)
            }

            is AddTransactionScreenUIEvent.OnTransactionDateTextFieldClick -> {
                uiStateEvents.setIsTransactionDatePickerDialogVisible(true)
            }

            is AddTransactionScreenUIEvent.OnCategoryTextFieldClick -> {
                uiStateEvents.setScreenBottomSheetType(
                    AddTransactionScreenBottomSheetType.SelectCategory
                )
            }

            is AddTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is AddTransactionScreenUIEvent.OnAccountFromUpdated -> {
                uiStateEvents.setAccountFrom(uiEvent.updatedAccountFrom)
            }

            is AddTransactionScreenUIEvent.OnAccountToUpdated -> {
                uiStateEvents.setAccountTo(uiEvent.updatedAccountTo)
            }

            is AddTransactionScreenUIEvent.OnAmountUpdated -> {
                uiStateEvents.setAmount(uiEvent.updatedAmount)
            }

            is AddTransactionScreenUIEvent.OnCategoryUpdated -> {
                uiStateEvents.setCategory(uiEvent.updatedCategory)
            }

            is AddTransactionScreenUIEvent.OnSelectedTransactionForIndexUpdated -> {
                uiStateEvents.setSelectedTransactionForIndex(uiEvent.updatedSelectedTransactionForIndex)
            }

            is AddTransactionScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                uiStateEvents.setSelectedTransactionTypeIndex(uiEvent.updatedSelectedTransactionTypeIndex)
            }

            is AddTransactionScreenUIEvent.OnTransactionTimePickerDismissed -> {
                uiStateEvents.setIsTransactionTimePickerDialogVisible(false)
            }

            is AddTransactionScreenUIEvent.OnTransactionDatePickerDismissed -> {
                uiStateEvents.setIsTransactionDatePickerDialogVisible(false)
            }

            is AddTransactionScreenUIEvent.OnTitleUpdated -> {
                uiStateEvents.setTitle(uiEvent.updatedTitle)
            }

            is AddTransactionScreenUIEvent.OnTransactionDateUpdated -> {
                uiStateEvents.setTransactionDate(uiEvent.updatedTransactionDate)
                uiStateEvents.setIsTransactionDatePickerDialogVisible(false)
            }

            is AddTransactionScreenUIEvent.OnTransactionTimeUpdated -> {
                uiStateEvents.setTransactionTime(uiEvent.updatedTransactionTime)
                uiStateEvents.setIsTransactionTimePickerDialogVisible(false)
            }

            is AddTransactionScreenUIEvent.OnSnackbarDismissed -> {
                uiStateEvents.resetScreenSnackbarType()
            }
        }
    }
}
