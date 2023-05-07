package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.transaction_list_item.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel.ViewTransactionScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel.ViewTransactionScreenViewModelImpl

@Composable
fun ViewTransactionScreen(
    screenViewModel: ViewTransactionScreenViewModel = hiltViewModel<ViewTransactionScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside ViewTransactionScreen",
    )
    val originalTransactionListItemData: TransactionListItemData? by screenViewModel.originalTransactionListItemData.collectAsStateWithLifecycle(
        initialValue = null,
    )
    val refundTransactionListItemData: List<TransactionListItemData>? by screenViewModel.refundTransactionListItemData.collectAsStateWithLifecycle(
        initialValue = null,
    )
    val transactionListItemData: TransactionListItemData? by screenViewModel.transactionListItemData.collectAsStateWithLifecycle(
        initialValue = null,
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.getTransactionData()
    }

    ViewTransactionScreenView(
        data = ViewTransactionScreenViewData(
            originalTransactionListItemData = originalTransactionListItemData,
            refundTransactionListItemData = refundTransactionListItemData,
            transactionListItemData = transactionListItemData,
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
