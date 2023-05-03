package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel.ViewTransactionScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel.ViewTransactionScreenViewModelImpl

@Composable
fun ViewTransactionScreen(
    screenViewModel: ViewTransactionScreenViewModel = hiltViewModel<ViewTransactionScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside ViewTransactionScreen",
    )
    val transactionData: TransactionData? by screenViewModel.transactionData.collectAsStateWithLifecycle(
        initialValue = null,
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.updateTransactionData()
    }

    ViewTransactionScreenView(
        data = ViewTransactionScreenViewData(
            dateTimeUtil = screenViewModel.dateTimeUtil,
            transactionData = transactionData,
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
        state = rememberCommonScreenViewState(),
    )
}
