package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel.ViewTransactionScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel.ViewTransactionScreenViewModelImpl

@Composable
fun ViewTransactionScreen(
    screenViewModel: ViewTransactionScreenViewModel = hiltViewModel<ViewTransactionScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside ViewTransactionScreen",
    )

    val screenUIData: ViewTransactionScreenUIData? by screenViewModel.screenUIData.collectAsStateWithLifecycle()

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.getTransactionData()
    }

    ViewTransactionScreenUI(
        data = screenUIData ?: ViewTransactionScreenUIData(),
        events = ViewTransactionScreenUIEvents(
            deleteTransaction = { transactionId ->
                screenViewModel.deleteTransaction(
                    id = transactionId,
                )
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.NavigateUp
                )
            },
            navigateToAddTransactionScreen = { transactionId ->
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.AddTransaction(
                        transactionId = transactionId,
                    )
                )
            },
            navigateToEditTransactionScreen = { transactionId ->
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.EditTransaction(
                        transactionId = transactionId,
                    )
                )
            },
            navigateUp = {
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.NavigateUp
                )
            },
        ),
        state = rememberCommonScreenUIState(),
    )
}
