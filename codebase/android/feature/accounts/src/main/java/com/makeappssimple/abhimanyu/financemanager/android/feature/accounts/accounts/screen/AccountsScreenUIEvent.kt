package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
sealed class AccountsScreenUIEvent : ScreenUIEvent {
    data object NavigateToAddAccountScreen : AccountsScreenUIEvent()
    data object NavigateUp : AccountsScreenUIEvent()

    data class DeleteAccount(
        val accountId: Int,
    ) : AccountsScreenUIEvent()

    data class NavigateToEditAccountScreen(
        val accountId: Int,
    ) : AccountsScreenUIEvent()

    data class SetDefaultAccountIdInDataStore(
        val defaultAccountId: Int,
    ) : AccountsScreenUIEvent()
}
