package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.event

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class EditAccountScreenUIEvent : ScreenUIEvent {
    public data object OnNavigationBackButtonClick : EditAccountScreenUIEvent()
    public data object OnClearBalanceAmountValueButtonClick : EditAccountScreenUIEvent()
    public data object OnClearMinimumAccountBalanceAmountValueButtonClick :
        EditAccountScreenUIEvent()

    public data object OnClearNameButtonClick : EditAccountScreenUIEvent()
    public data object OnCtaButtonClick : EditAccountScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : EditAccountScreenUIEvent()

    public data class OnBalanceAmountValueUpdated(
        val updatedBalanceAmountValue: TextFieldValue,
    ) : EditAccountScreenUIEvent()

    public data class OnMinimumAccountBalanceAmountValueUpdated(
        val updatedMinimumAccountBalanceAmountValue: TextFieldValue,
    ) : EditAccountScreenUIEvent()

    public data class OnNameUpdated(
        val updatedName: TextFieldValue,
    ) : EditAccountScreenUIEvent()

    public data class OnSelectedAccountTypeIndexUpdated(
        val updatedIndex: Int,
    ) : EditAccountScreenUIEvent()
}
