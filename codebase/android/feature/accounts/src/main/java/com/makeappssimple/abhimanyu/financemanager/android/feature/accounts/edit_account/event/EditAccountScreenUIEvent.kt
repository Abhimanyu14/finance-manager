package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.event

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
internal sealed class EditAccountScreenUIEvent : ScreenUIEvent {
    data object OnNavigationBackButtonClick : EditAccountScreenUIEvent()
    data object OnClearBalanceAmountValueButtonClick : EditAccountScreenUIEvent()
    data object OnClearMinimumAccountBalanceAmountValueButtonClick :
        EditAccountScreenUIEvent()

    data object OnClearNameButtonClick : EditAccountScreenUIEvent()
    data object OnCtaButtonClick : EditAccountScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : EditAccountScreenUIEvent()

    data class OnBalanceAmountValueUpdated(
        val updatedBalanceAmountValue: TextFieldValue,
    ) : EditAccountScreenUIEvent()

    data class OnMinimumAccountBalanceAmountValueUpdated(
        val updatedMinimumAccountBalanceAmountValue: TextFieldValue,
    ) : EditAccountScreenUIEvent()

    data class OnNameUpdated(
        val updatedName: TextFieldValue,
    ) : EditAccountScreenUIEvent()

    data class OnSelectedAccountTypeIndexUpdated(
        val updatedIndex: Int,
    ) : EditAccountScreenUIEvent()
}
