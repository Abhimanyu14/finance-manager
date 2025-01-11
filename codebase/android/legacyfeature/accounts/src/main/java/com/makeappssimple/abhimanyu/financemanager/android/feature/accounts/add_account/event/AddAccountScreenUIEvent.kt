package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.event

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
internal sealed class AddAccountScreenUIEvent : ScreenUIEvent {
    data object OnNavigationBackButtonClick : AddAccountScreenUIEvent()
    data object OnClearBalanceAmountValueButtonClick : AddAccountScreenUIEvent()
    data object OnClearMinimumAccountBalanceAmountValueButtonClick :
        AddAccountScreenUIEvent()

    data object OnClearNameButtonClick : AddAccountScreenUIEvent()
    data object OnCtaButtonClick : AddAccountScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : AddAccountScreenUIEvent()

    data class OnBalanceAmountValueUpdated(
        val updatedBalanceAmountValue: TextFieldValue,
    ) : AddAccountScreenUIEvent()

    data class OnMinimumAccountBalanceAmountValueUpdated(
        val updatedMinimumAccountBalanceAmountValue: TextFieldValue,
    ) : AddAccountScreenUIEvent()

    data class OnNameUpdated(
        val updatedName: TextFieldValue,
    ) : AddAccountScreenUIEvent()

    data class OnSelectedAccountTypeIndexUpdated(
        val updatedIndex: Int,
    ) : AddAccountScreenUIEvent()
}
