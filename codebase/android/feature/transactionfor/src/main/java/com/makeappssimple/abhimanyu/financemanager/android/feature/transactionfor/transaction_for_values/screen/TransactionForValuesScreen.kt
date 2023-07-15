package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.viewmodel.TransactionForValuesScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.viewmodel.TransactionForValuesScreenViewModelImpl

@Composable
fun TransactionForValuesScreen(
    screenViewModel: TransactionForValuesScreenViewModel = hiltViewModel<TransactionForValuesScreenViewModelImpl>(),
) {
    screenViewModel.myLogger.logError(
        message = "Inside TransactionForValuesScreen",
    )

    val screenUIData: MyResult<TransactionForValuesScreenUIData>? by screenViewModel.screenUIData.collectAsStateWithLifecycle()

    TransactionForValuesScreenUI(
        events = TransactionForValuesScreenUIEvents(
            deleteTransactionFor = screenViewModel::deleteTransactionFor,
            navigateToAddTransactionForScreen = screenViewModel::navigateToAddTransactionForScreen,
            navigateToEditTransactionForScreen = screenViewModel::navigateToEditTransactionForScreen,
            navigateUp = screenViewModel::navigateUp,
        ),
        uiState = rememberTransactionForValuesScreenUIState(
            data = screenUIData,
        ),
        state = rememberCommonScreenUIState(),
    )
}
