package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.view_transaction_for.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.view_transaction_for.viewmodel.ViewTransactionForScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.view_transaction_for.viewmodel.ViewTransactionForScreenViewModelImpl

@Composable
fun ViewTransactionForScreen(
    screenViewModel: ViewTransactionForScreenViewModel = hiltViewModel<ViewTransactionForScreenViewModelImpl>(),
) {
    logError(
        message = "Inside ViewTransactionForScreen",
    )
    val transactionForValues: List<TransactionFor> by screenViewModel.transactionForValues.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )

    ViewTransactionForScreenView(
        data = ViewTransactionForScreenViewData(
            transactionForValues = transactionForValues,
            navigationManager = screenViewModel.navigationManager,
        ),
        state = rememberCommonScreenViewState(),
    )
}
