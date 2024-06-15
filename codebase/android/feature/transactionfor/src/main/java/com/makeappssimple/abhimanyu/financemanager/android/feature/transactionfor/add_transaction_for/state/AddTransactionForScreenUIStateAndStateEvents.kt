package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents

@Stable
internal class AddTransactionForScreenUIStateAndStateEvents(
    val state: AddTransactionForScreenUIState = AddTransactionForScreenUIState(),
    val events: AddTransactionForScreenUIStateEvents = AddTransactionForScreenUIStateEvents(),
) : ScreenUIStateAndStateEvents
