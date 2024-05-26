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
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: EditTransactionForScreenUIEvent ->
            when (uiEvent) {
                is EditTransactionForScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is EditTransactionForScreenUIEvent.OnBottomSheetDismissed -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is EditTransactionForScreenUIEvent.OnCtaButtonClick -> {
                    viewModel.updateTransactionFor(
                        title = uiStateAndEvents.state.title.text,
                    )
                }

                is EditTransactionForScreenUIEvent.OnClearTitleButtonClick -> {
                    uiStateAndEvents.events.setTitle(
                        uiStateAndEvents.state.title.copy(
                            text = "",
                        )
                    )
                }

                is EditTransactionForScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is EditTransactionForScreenUIEvent.OnTitleUpdated -> {
                    uiStateAndEvents.events.setTitle(uiEvent.updatedTitle)
                }
            }
        }
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
        handleUIEvent = handleUIEvent,
    )
}
