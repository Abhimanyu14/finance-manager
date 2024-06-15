package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents

@Stable
internal class EditTransactionForScreenUIStateAndStateEvents(
    val state: EditTransactionForScreenUIState = EditTransactionForScreenUIState(),
    val events: EditTransactionForScreenUIStateEvents = EditTransactionForScreenUIStateEvents(),
) : ScreenUIStateAndStateEvents
