package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents

@Stable
internal class EditAccountScreenUIStateAndStateEvents(
    val state: EditAccountScreenUIState = EditAccountScreenUIState(),
    val events: EditAccountScreenUIStateEvents = EditAccountScreenUIStateEvents(),
) : ScreenUIStateAndStateEvents
