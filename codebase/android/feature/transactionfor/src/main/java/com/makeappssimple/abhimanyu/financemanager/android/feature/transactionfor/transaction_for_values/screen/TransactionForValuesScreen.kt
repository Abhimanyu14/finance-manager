package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.viewmodel.TransactionForValuesScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.viewmodel.TransactionForValuesScreenViewModelImpl

@Composable
fun TransactionForValuesScreen(
    screenViewModel: TransactionForValuesScreenViewModel = hiltViewModel<TransactionForValuesScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside TransactionForValuesScreen",
    )
    val screenUIData: TransactionForValuesScreenUIData? by screenViewModel.screenUIData.collectAsStateWithLifecycle()

    TransactionForValuesScreenUI(
        events = TransactionForValuesScreenUIEvents(
            deleteTransactionFor = { transactionForId ->
                screenViewModel.deleteTransactionFor(
                    id = transactionForId,
                )
            },
            navigateToAddTransactionForScreen = {
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.AddTransactionFor
                )
            },
            navigateToEditTransactionForScreen = {
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.EditTransactionFor(
                        transactionForId = it,
                    )
                )
            },
            navigateUp = {
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.NavigateUp
                )
            },
        ),
        uiState = rememberTransactionForValuesScreenUIState(
            data = screenUIData ?: TransactionForValuesScreenUIData(),
        ),
        state = rememberCommonScreenUIState(),
    )
}
