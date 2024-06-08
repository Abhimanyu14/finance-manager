package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel.AddAccountScreenViewModel

public class AddAccountScreenUIEventHandler internal constructor(
    private val viewModel: AddAccountScreenViewModel,
    private val uiStateAndEvents: AddAccountScreenUIStateAndEvents,
) {
    public fun handleUIEvent(
        uiEvent: AddAccountScreenUIEvent,
    ) {
        when (uiEvent) {
            is AddAccountScreenUIEvent.OnCtaButtonClick -> {
                uiStateAndEvents.state.selectedAccountType?.let { accountType ->
                    val minimumAccountBalanceAmount =
                        if (accountType == AccountType.BANK) {
                            Amount(
                                value = uiStateAndEvents.state.minimumAccountBalanceTextFieldValue.text.toLongOrZero(),
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
                            name = uiStateAndEvents.state.nameTextFieldValue.text,
                        ),
                    )
                }
            }

            is AddAccountScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is AddAccountScreenUIEvent.OnClearMinimumAccountBalanceAmountValueButtonClick -> {
                uiStateAndEvents.events.clearMinimumAccountBalanceAmountValue()
            }

            is AddAccountScreenUIEvent.OnClearNameButtonClick -> {
                uiStateAndEvents.events.clearName()
            }

            is AddAccountScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                viewModel.navigateUp()
            }

            is AddAccountScreenUIEvent.OnMinimumAccountBalanceAmountValueUpdated -> {
                uiStateAndEvents.events.updateMinimumAccountBalanceAmountValue(
                    uiEvent.updatedMinimumAccountBalanceAmountValue,
                )
            }

            is AddAccountScreenUIEvent.OnNameUpdated -> {
                uiStateAndEvents.events.updateName(
                    uiEvent.updatedName,
                )
            }

            is AddAccountScreenUIEvent.OnSelectedAccountTypeIndexUpdated -> {
                uiStateAndEvents.events.updateSelectedAccountTypeIndex(
                    uiEvent.updatedIndex,
                )
            }

            else -> {
                // No-op
            }
        }
    }
}
