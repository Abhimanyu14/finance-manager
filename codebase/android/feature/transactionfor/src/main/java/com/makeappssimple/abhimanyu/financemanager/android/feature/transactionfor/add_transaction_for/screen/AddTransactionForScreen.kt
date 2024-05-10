package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen.AddOrEditTransactionForScreenUI
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen.AddOrEditTransactionForScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen.AddOrEditTransactionForScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen.rememberAddOrEditTransactionForScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.viewmodel.AddOrEditTransactionForScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.viewmodel.AddOrEditTransactionForScreenViewModelImpl

@Composable
public fun AddTransactionForScreen(
    screenViewModel: AddOrEditTransactionForScreenViewModel = hiltViewModel<AddOrEditTransactionForScreenViewModelImpl>(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AddTransactionForScreen",
    )

    val screenUIData: MyResult<AddOrEditTransactionForScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiState = rememberAddOrEditTransactionForScreenUIState(
        data = screenUIData,
        isEdit = false,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiState,
    ) {
        { uiEvent: AddOrEditTransactionForScreenUIEvent ->
            when (uiEvent) {
                is AddOrEditTransactionForScreenUIEvent.OnCtaButtonClick -> {
                    viewModel.insertTransactionFor()
                }

                else -> {
                    viewModel.handleUIEvent(
                        uiEvent = uiEvent,
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

    AddOrEditTransactionForScreenUI(
        uiState = uiState,
        handleUIEvent = handleUIEvent,
    )
}
