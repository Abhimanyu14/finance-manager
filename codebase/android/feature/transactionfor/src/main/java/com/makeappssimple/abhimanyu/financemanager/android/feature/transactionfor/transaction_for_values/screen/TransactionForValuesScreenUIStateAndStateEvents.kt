package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents

@Stable
internal class TransactionForValuesScreenUIStateAndStateEvents(
    val state: TransactionForValuesScreenUIState = TransactionForValuesScreenUIState(),
    val events: TransactionForValuesScreenUIStateEvents = TransactionForValuesScreenUIStateEvents(),
) : ScreenUIStateAndStateEvents
