package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.screen

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

@Composable
public fun EditTransactionForScreen(
    screenViewModel: AddOrEditTransactionForScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside EditTransactionForScreen",
    )

    val screenUIData: MyResult<AddOrEditTransactionForScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiState = rememberAddOrEditTransactionForScreenUIState(
        data = screenUIData,
        isEdit = true,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiState,
    ) {
        { uiEvent: AddOrEditTransactionForScreenUIEvent ->
            when (uiEvent) {
                is AddOrEditTransactionForScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiState.resetScreenBottomSheetType()
                }

                is AddOrEditTransactionForScreenUIEvent.OnBottomSheetDismissed -> {
                    uiState.resetScreenBottomSheetType()
                }
                
                is AddOrEditTransactionForScreenUIEvent.OnCtaButtonClick -> {
                    viewModel.updateTransactionFor()
                }

                is AddOrEditTransactionForScreenUIEvent.OnClearTitleButtonClick -> {
                    viewModel.clearTitle()
                }

                is AddOrEditTransactionForScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is AddOrEditTransactionForScreenUIEvent.OnTitleUpdated -> {
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

    AddOrEditTransactionForScreenUI(
        uiState = uiState,
        handleUIEvent = handleUIEvent,
    )
}
