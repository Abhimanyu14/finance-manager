package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.event

import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.bottomsheet.AddTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state.AddTransactionScreenUIStateAndStateEvents

internal class AddTransactionScreenEventHandler(
    private val uiStateAndStateEvents: AddTransactionScreenUIStateAndStateEvents,
) {
    fun handleUIEvent(
        uiEvent: AddTransactionScreenUIEvent,
    ) {
        when (uiEvent) {
            is AddTransactionScreenUIEvent.OnBottomSheetDismissed -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is AddTransactionScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is AddTransactionScreenUIEvent.OnCtaButtonClick -> {
                uiStateAndStateEvents.events.insertTransaction()
            }

            is AddTransactionScreenUIEvent.OnClearAmountButtonClick -> {
                uiStateAndStateEvents.events.clearAmount()
            }

            is AddTransactionScreenUIEvent.OnClearTitleButtonClick -> {
                uiStateAndStateEvents.events.clearTitle()
            }

            is AddTransactionScreenUIEvent.OnAccountFromTextFieldClick -> {
                uiStateAndStateEvents.events.setScreenBottomSheetType(
                    AddTransactionScreenBottomSheetType.SelectAccountFrom
                )
            }

            is AddTransactionScreenUIEvent.OnAccountToTextFieldClick -> {
                uiStateAndStateEvents.events.setScreenBottomSheetType(
                    AddTransactionScreenBottomSheetType.SelectAccountTo
                )
            }

            is AddTransactionScreenUIEvent.OnTransactionTimeTextFieldClick -> {
                uiStateAndStateEvents.events.setIsTransactionTimePickerDialogVisible(true)
            }

            is AddTransactionScreenUIEvent.OnTransactionDateTextFieldClick -> {
                uiStateAndStateEvents.events.setIsTransactionDatePickerDialogVisible(true)
            }

            is AddTransactionScreenUIEvent.OnCategoryTextFieldClick -> {
                uiStateAndStateEvents.events.setScreenBottomSheetType(
                    AddTransactionScreenBottomSheetType.SelectCategory
                )
            }

            is AddTransactionScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateAndStateEvents.events.navigateUp()
            }

            is AddTransactionScreenUIEvent.OnAccountFromUpdated -> {
                uiStateAndStateEvents.events.setAccountFrom(uiEvent.updatedAccountFrom)
            }

            is AddTransactionScreenUIEvent.OnAccountToUpdated -> {
                uiStateAndStateEvents.events.setAccountTo(uiEvent.updatedAccountTo)
            }

            is AddTransactionScreenUIEvent.OnAmountUpdated -> {
                uiStateAndStateEvents.events.setAmount(uiEvent.updatedAmount)
            }

            is AddTransactionScreenUIEvent.OnCategoryUpdated -> {
                uiStateAndStateEvents.events.setCategory(uiEvent.updatedCategory)
            }

            is AddTransactionScreenUIEvent.OnSelectedTransactionForIndexUpdated -> {
                uiStateAndStateEvents.events.setSelectedTransactionForIndex(uiEvent.updatedSelectedTransactionForIndex)
            }

            is AddTransactionScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                uiStateAndStateEvents.events.setSelectedTransactionTypeIndex(uiEvent.updatedSelectedTransactionTypeIndex)
            }

            is AddTransactionScreenUIEvent.OnTransactionTimePickerDismissed -> {
                uiStateAndStateEvents.events.setIsTransactionTimePickerDialogVisible(false)
            }

            is AddTransactionScreenUIEvent.OnTransactionDatePickerDismissed -> {
                uiStateAndStateEvents.events.setIsTransactionDatePickerDialogVisible(false)
            }

            is AddTransactionScreenUIEvent.OnTitleUpdated -> {
                uiStateAndStateEvents.events.setTitle(uiEvent.updatedTitle)
            }

            is AddTransactionScreenUIEvent.OnTransactionDateUpdated -> {
                uiStateAndStateEvents.events.setTransactionDate(uiEvent.updatedTransactionDate)
                uiStateAndStateEvents.events.setIsTransactionDatePickerDialogVisible(false)
            }

            is AddTransactionScreenUIEvent.OnTransactionTimeUpdated -> {
                uiStateAndStateEvents.events.setTransactionTime(uiEvent.updatedTransactionTime)
                uiStateAndStateEvents.events.setIsTransactionTimePickerDialogVisible(false)
            }
        }
    }
}
