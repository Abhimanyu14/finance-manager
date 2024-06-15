package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents

@Stable
internal class TransactionsScreenUIStateAndStateEvents(
    val state: TransactionsScreenUIState = TransactionsScreenUIState(),
    val events: TransactionsScreenUIStateEvents = TransactionsScreenUIStateEvents(),
) : ScreenUIStateAndStateEvents
