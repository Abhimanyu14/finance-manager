package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class AccountsScreenUIEvent : ScreenUIEvent {
    public data object NavigateToAddAccountScreen : AccountsScreenUIEvent()
    public data object NavigateUp : AccountsScreenUIEvent()

    public data class DeleteAccount(
        val accountId: Int,
    ) : AccountsScreenUIEvent()

    public data class NavigateToEditAccountScreen(
        val accountId: Int,
    ) : AccountsScreenUIEvent()

    public data class SetDefaultAccountIdInDataStore(
        val defaultAccountId: Int,
    ) : AccountsScreenUIEvent()
}
