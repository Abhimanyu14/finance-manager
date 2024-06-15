package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents

@Stable
internal class ViewTransactionScreenUIStateAndStateEvents(
    val state: ViewTransactionScreenUIState = ViewTransactionScreenUIState(),
    val events: ViewTransactionScreenUIStateEvents = ViewTransactionScreenUIStateEvents(),
) : ScreenUIStateAndStateEvents
