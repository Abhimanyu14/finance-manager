package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel.ViewTransactionScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel.ViewTransactionScreenViewModelImpl

@Composable
fun ViewTransactionScreen(
    screenViewModel: ViewTransactionScreenViewModel = hiltViewModel<ViewTransactionScreenViewModelImpl>(),
) {
    logError(
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
            navigationManager = screenViewModel.navigationManager,
            transactionData = transactionData,
            deleteTransaction = { transactionId ->
                screenViewModel.deleteTransaction(
                    id = transactionId,
                )
                navigateUp(
                    navigationManager = screenViewModel.navigationManager,
                )
            },
        ),
        state = rememberCommonScreenViewState(),
    )
}
