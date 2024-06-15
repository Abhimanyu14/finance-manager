package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel.AddAccountScreenViewModel

public class AddAccountScreenUIEventHandler internal constructor(
    private val viewModel: AddAccountScreenViewModel,
    private val uiStateAndStateEvents: AddAccountScreenUIStateAndStateEvents,
) {
    public fun handleUIEvent(
        uiEvent: AddAccountScreenUIEvent,
    ) {
        when (uiEvent) {
            is AddAccountScreenUIEvent.OnCtaButtonClick -> {
                uiStateAndStateEvents.state.selectedAccountType?.let { accountType ->
                    val minimumAccountBalanceAmount =
                        if (accountType == AccountType.BANK) {
                            Amount(
                                value = uiStateAndStateEvents.state.minimumAccountBalanceTextFieldValue.text.toLongOrZero(),
                            )
                        } else {
                            null
                        }

                    viewModel.insertAccount(
                        account = Account(
                            balanceAmount = Amount(
                                value = 0L,
                            ),
                            type = accountType,
                            minimumAccountBalanceAmount = minimumAccountBalanceAmount,
                            name = uiStateAndStateEvents.state.nameTextFieldValue.text,
                        ),
                    )
                }
            }

            is AddAccountScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is AddAccountScreenUIEvent.OnClearMinimumAccountBalanceAmountValueButtonClick -> {
                uiStateAndStateEvents.events.clearMinimumAccountBalanceAmountValue()
            }

            is AddAccountScreenUIEvent.OnClearNameButtonClick -> {
                uiStateAndStateEvents.events.clearName()
            }

            is AddAccountScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                viewModel.navigateUp()
            }

            is AddAccountScreenUIEvent.OnMinimumAccountBalanceAmountValueUpdated -> {
                uiStateAndStateEvents.events.updateMinimumAccountBalanceAmountValue(
                    uiEvent.updatedMinimumAccountBalanceAmountValue,
                )
            }

            is AddAccountScreenUIEvent.OnNameUpdated -> {
                uiStateAndStateEvents.events.updateName(
                    uiEvent.updatedName,
                )
            }

            is AddAccountScreenUIEvent.OnSelectedAccountTypeIndexUpdated -> {
                uiStateAndStateEvents.events.updateSelectedAccountTypeIndex(
                    uiEvent.updatedIndex,
                )
            }

            else -> {
                // No-op
            }
        }
    }
}
