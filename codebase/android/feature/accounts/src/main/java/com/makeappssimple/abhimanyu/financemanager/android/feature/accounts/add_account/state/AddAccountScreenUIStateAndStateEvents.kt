package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents

@Stable
internal class AddAccountScreenUIStateAndStateEvents(
    val state: AddAccountScreenUIState = AddAccountScreenUIState(),
    val events: AddAccountScreenUIStateEvents = AddAccountScreenUIStateEvents(),
) : ScreenUIStateAndStateEvents
