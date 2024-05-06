package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class AddOrEditAccountScreenUIEvent : ScreenUIEvent {
    public data object OnClearBalanceAmountValueButtonClick : AddOrEditAccountScreenUIEvent()
    public data object OnClearMinimumAccountBalanceAmountValueButtonClick :
        AddOrEditAccountScreenUIEvent()

    public data object OnClearNameButtonClick : AddOrEditAccountScreenUIEvent()
    public data object OnCtaButtonClick : AddOrEditAccountScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : AddOrEditAccountScreenUIEvent()

    public data class OnBalanceAmountValueUpdated(
        val updatedBalanceAmountValue: TextFieldValue,
    ) : AddOrEditAccountScreenUIEvent()

    public data class OnMinimumAccountBalanceAmountValueUpdated(
        val updatedMinimumAccountBalanceAmountValue: TextFieldValue,
    ) : AddOrEditAccountScreenUIEvent()

    public data class OnNameUpdated(
        val updatedName: TextFieldValue,
    ) : AddOrEditAccountScreenUIEvent()

    public data class OnSelectedAccountTypeIndexUpdated(
        val updatedIndex: Int,
    ) : AddOrEditAccountScreenUIEvent()
}
