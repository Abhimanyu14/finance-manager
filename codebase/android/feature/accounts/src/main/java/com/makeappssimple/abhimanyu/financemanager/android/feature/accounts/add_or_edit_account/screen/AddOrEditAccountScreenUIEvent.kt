package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_or_edit_account.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
sealed class AddOrEditAccountScreenUIEvent : ScreenUIEvent {
    data object ClearBalanceAmountValue : AddOrEditAccountScreenUIEvent()
    data object ClearMinimumAccountBalanceAmountValue : AddOrEditAccountScreenUIEvent()
    data object ClearName : AddOrEditAccountScreenUIEvent()
    data object NavigateUp : AddOrEditAccountScreenUIEvent()
    data object OnCtaButtonClick : AddOrEditAccountScreenUIEvent()

    data class UpdateBalanceAmountValue(
        val updatedBalanceAmountValue: TextFieldValue,
    ) : AddOrEditAccountScreenUIEvent()

    data class UpdateMinimumAccountBalanceAmountValue(
        val updatedMinimumAccountBalanceAmountValue: TextFieldValue,
    ) : AddOrEditAccountScreenUIEvent()

    data class UpdateName(
        val updatedName: TextFieldValue,
    ) : AddOrEditAccountScreenUIEvent()

    data class UpdateSelectedAccountTypeIndex(
        val updatedIndex: Int,
    ) : AddOrEditAccountScreenUIEvent()
}
