package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextRange
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.viewmodel.EditTransactionForScreenViewModel
import kotlinx.collections.immutable.ImmutableList

@Composable
public fun EditTransactionForScreen(
    screenViewModel: EditTransactionForScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside EditTransactionForScreen",
    )

    // region view model data
    val transactionForValues: ImmutableList<TransactionFor> by viewModel.transactionForValues.collectAsStateWithLifecycle()
    val transactionFor: TransactionFor? by viewModel.transactionFor.collectAsStateWithLifecycle()
    // endregion

    val uiStateAndEvents = rememberEditTransactionForScreenUIStateAndEvents(
        transactionForValues = transactionForValues,
        transactionFor = transactionFor,
    )
    val screenUIEventHandler = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        EditTransactionForScreenUIEventHandler(
            viewModel = viewModel,
            uiStateAndEvents = uiStateAndEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        viewModel.initViewModel()
    }

    LaunchedEffect(transactionFor) {
        transactionFor?.let { transactionFor ->
            uiStateAndEvents.events.setTitle(
                uiStateAndEvents.state.title.copy(
                    text = transactionFor.title,
                    selection = TextRange(transactionFor.title.length),
                )
            )
        }
    }

    EditTransactionForScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
