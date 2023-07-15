package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel.ViewTransactionScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel.ViewTransactionScreenViewModelImpl

@Composable
fun ViewTransactionScreen(
    screenViewModel: ViewTransactionScreenViewModel = hiltViewModel<ViewTransactionScreenViewModelImpl>(),
) {
    screenViewModel.myLogger.logError(
        message = "Inside ViewTransactionScreen",
    )

    val screenUIData: MyResult<ViewTransactionScreenUIData>? by screenViewModel.screenUIData.collectAsStateWithLifecycle()

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.getTransactionData()
    }

    ViewTransactionScreenUI(
        events = ViewTransactionScreenUIEvents(
            deleteTransaction = screenViewModel::deleteTransaction,
            navigateToAddTransactionScreen = screenViewModel::navigateToAddTransactionScreen,
            navigateToEditTransactionScreen = screenViewModel::navigateToEditTransactionScreen,
            navigateUp = screenViewModel::navigateUp,
        ),
        uiState = rememberViewTransactionScreenUIState(
            data = screenUIData,
        ),
        state = rememberCommonScreenUIState(),
    )
}
