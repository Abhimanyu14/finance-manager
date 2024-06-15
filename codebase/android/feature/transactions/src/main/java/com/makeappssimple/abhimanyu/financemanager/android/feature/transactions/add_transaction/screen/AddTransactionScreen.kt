package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.event.AddTransactionScreenEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state.AddTransactionScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state.rememberAddTransactionScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenInitialData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenViewModel
import kotlinx.collections.immutable.ImmutableList

@Suppress("ViewModelForwarding")
@Composable
public fun AddTransactionScreen(
    viewModel: AddTransactionScreenViewModel = hiltViewModel(),
) {
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AddTransactionScreen",
    )

    // region view model data
    val addTransactionScreenInitialData: AddTransactionScreenInitialData? by viewModel.addTransactionScreenInitialData.collectAsStateWithLifecycle()
    val titleSuggestions: ImmutableList<String> by viewModel.titleSuggestions.collectAsStateWithLifecycle()
    // endregion

    val (uiState, uiStateEvents) = rememberAddTransactionScreenUIStateAndStateEvents(
        addTransactionScreenInitialData = addTransactionScreenInitialData,
        titleSuggestions = titleSuggestions,
    )

    val eventHandler: AddTransactionScreenEventHandler = remember {
        AddTransactionScreenEventHandler(
            uiState = uiState,
            uiStateEvents = uiStateEvents,
            viewModel = viewModel,
            addTransactionScreenInitialData = addTransactionScreenInitialData,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        viewModel.initViewModel()
    }

    LaunchedEffect(
        key1 = addTransactionScreenInitialData,
    ) {
        handleAddTransactionScreenInitialDataFetched(
            addTransactionScreenInitialData = addTransactionScreenInitialData,
            uiStateEvents = uiStateEvents,
            viewModel = viewModel,
        )
    }

    AddTransactionScreenUI(
        uiState = uiState,
        handleUIEvent = { uiEvent ->
            eventHandler.handleUIEvent(uiEvent)
        },
    )
}

private fun handleAddTransactionScreenInitialDataFetched(
    addTransactionScreenInitialData: AddTransactionScreenInitialData?,
    uiStateEvents: AddTransactionScreenUIStateEvents,
    viewModel: AddTransactionScreenViewModel,
) {
    addTransactionScreenInitialData ?: return
    val originalTransactionData = addTransactionScreenInitialData.originalTransactionData
    if (originalTransactionData != null) {
        uiStateEvents.setCategory(originalTransactionData.category)
        viewModel.setCategory(originalTransactionData.category)
        uiStateEvents.setAccountFrom(originalTransactionData.accountFrom)
        uiStateEvents.setAccountTo(originalTransactionData.accountTo)
        uiStateEvents.setSelectedTransactionTypeIndex(
            // TODO(Abhi): Move this logic outside
            addTransactionScreenInitialData.validTransactionTypesForNewTransaction.indexOf(
                element = TransactionType.REFUND,
            )
        )
        uiStateEvents.setSelectedTransactionForIndex(
            addTransactionScreenInitialData.transactionForValues.indexOf(
                element = addTransactionScreenInitialData.transactionForValues.firstOrNull {
                    it.id == originalTransactionData.transaction.id
                },
            )
        )
    } else {
        uiStateEvents.setCategory(addTransactionScreenInitialData.defaultExpenseCategory)
        viewModel.setCategory(addTransactionScreenInitialData.defaultExpenseCategory)
        uiStateEvents.setAccountFrom(addTransactionScreenInitialData.defaultAccount)
        uiStateEvents.setAccountTo(addTransactionScreenInitialData.defaultAccount)
        uiStateEvents.setSelectedTransactionTypeIndex(
            // TODO(Abhi): Move this logic outside
            addTransactionScreenInitialData.validTransactionTypesForNewTransaction.indexOf(
                element = TransactionType.EXPENSE,
            )
        )
    }
}
