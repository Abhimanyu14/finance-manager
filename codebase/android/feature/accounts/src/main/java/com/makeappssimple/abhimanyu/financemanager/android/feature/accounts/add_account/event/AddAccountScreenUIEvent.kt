package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.event

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class AddAccountScreenUIEvent : ScreenUIEvent {
    public data object OnNavigationBackButtonClick : AddAccountScreenUIEvent()
    public data object OnClearBalanceAmountValueButtonClick : AddAccountScreenUIEvent()
    public data object OnClearMinimumAccountBalanceAmountValueButtonClick :
        AddAccountScreenUIEvent()

    public data object OnClearNameButtonClick : AddAccountScreenUIEvent()
    public data object OnCtaButtonClick : AddAccountScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : AddAccountScreenUIEvent()

    public data class OnBalanceAmountValueUpdated(
        val updatedBalanceAmountValue: TextFieldValue,
    ) : AddAccountScreenUIEvent()

    public data class OnMinimumAccountBalanceAmountValueUpdated(
        val updatedMinimumAccountBalanceAmountValue: TextFieldValue,
    ) : AddAccountScreenUIEvent()

    public data class OnNameUpdated(
        val updatedName: TextFieldValue,
    ) : AddAccountScreenUIEvent()

    public data class OnSelectedAccountTypeIndexUpdated(
        val updatedIndex: Int,
    ) : AddAccountScreenUIEvent()
}
