package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.viewmodel.AddTransactionForScreenViewModel

@Composable
public fun AddTransactionForScreen(
    screenViewModel: AddTransactionForScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AddTransactionForScreen",
    )

    val screenUIData: MyResult<AddTransactionForScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiStateAndEvents = rememberAddTransactionForScreenUIStateAndEvents(
        data = screenUIData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: AddTransactionForScreenUIEvent ->
            when (uiEvent) {
                is AddTransactionForScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AddTransactionForScreenUIEvent.OnBottomSheetDismissed -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AddTransactionForScreenUIEvent.OnCtaButtonClick -> {
                    viewModel.insertTransactionFor()
                }

                is AddTransactionForScreenUIEvent.OnClearTitleButtonClick -> {
                    viewModel.clearTitle()
                }

                is AddTransactionForScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is AddTransactionForScreenUIEvent.OnTitleUpdated -> {
                    viewModel.updateTitle(
                        updatedTitle = uiEvent.updatedTitle,
                    )
                }
            }
        }
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        viewModel.initViewModel()
    }

    AddTransactionForScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}
