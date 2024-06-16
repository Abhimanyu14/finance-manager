package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents

@Stable
internal data class AddTransactionScreenUIStateAndStateEvents(
    val state: AddTransactionScreenUIState = AddTransactionScreenUIState(),
    val events: AddTransactionScreenUIStateEvents = AddTransactionScreenUIStateEvents(),
) : ScreenUIStateAndStateEvents
