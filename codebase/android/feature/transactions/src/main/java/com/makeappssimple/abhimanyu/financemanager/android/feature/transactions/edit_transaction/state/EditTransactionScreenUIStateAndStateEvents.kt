package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents

@Stable
internal class EditTransactionScreenUIStateAndStateEvents(
    val state: EditTransactionScreenUIState = EditTransactionScreenUIState(),
    val events: EditTransactionScreenUIStateEvents = EditTransactionScreenUIStateEvents(),
) : ScreenUIStateAndStateEvents
