package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class AddOrEditAccountScreenUIEvent : ScreenUIEvent {
    public data object ClearBalanceAmountValue : AddOrEditAccountScreenUIEvent()
    public data object ClearMinimumAccountBalanceAmountValue : AddOrEditAccountScreenUIEvent()
    public data object ClearName : AddOrEditAccountScreenUIEvent()
    public data object NavigateUp : AddOrEditAccountScreenUIEvent()
    public data object OnCtaButtonClick : AddOrEditAccountScreenUIEvent()

    public data class UpdateBalanceAmountValue(
        val updatedBalanceAmountValue: TextFieldValue,
    ) : AddOrEditAccountScreenUIEvent()

    public data class UpdateMinimumAccountBalanceAmountValue(
        val updatedMinimumAccountBalanceAmountValue: TextFieldValue,
    ) : AddOrEditAccountScreenUIEvent()

    public data class UpdateName(
        val updatedName: TextFieldValue,
    ) : AddOrEditAccountScreenUIEvent()

    public data class UpdateSelectedAccountTypeIndex(
        val updatedIndex: Int,
    ) : AddOrEditAccountScreenUIEvent()
}
