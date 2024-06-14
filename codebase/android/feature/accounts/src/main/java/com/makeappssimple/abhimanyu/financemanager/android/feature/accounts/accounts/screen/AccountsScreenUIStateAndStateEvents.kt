package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents

@Stable
internal class AccountsScreenUIStateAndStateEvents(
    val state: AccountsScreenUIState = AccountsScreenUIState(),
    val events: AccountsScreenUIStateEvents = AccountsScreenUIStateEvents(),
) : ScreenUIStateAndEvents
