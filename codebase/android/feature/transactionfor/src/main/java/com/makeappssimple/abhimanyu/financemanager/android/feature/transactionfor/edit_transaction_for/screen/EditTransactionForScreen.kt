package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen.AddOrEditTransactionForScreenUI
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen.AddOrEditTransactionForScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen.AddOrEditTransactionForScreenUIEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen.rememberAddOrEditTransactionForScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.viewmodel.AddOrEditTransactionForScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.viewmodel.AddOrEditTransactionForScreenViewModelImpl

@Composable
fun EditTransactionForScreen(
    screenViewModel: AddOrEditTransactionForScreenViewModel = hiltViewModel<AddOrEditTransactionForScreenViewModelImpl>(),
) {
    screenViewModel.myLogger.logError(
        message = "Inside EditTransactionForScreen",
    )

    val screenUIData: MyResult<AddOrEditTransactionForScreenUIData>? by screenViewModel.screenUIData.collectAsStateWithLifecycle()

    AddOrEditTransactionForScreenUI(
        events = AddOrEditTransactionForScreenUIEvents(
            clearTitle = screenViewModel::clearTitle,
            navigateUp = screenViewModel::navigateUp,
            onCtaButtonClick = screenViewModel::updateTransactionFor,
            updateTitle = screenViewModel::updateTitle,
        ),
        uiState = rememberAddOrEditTransactionForScreenUIState(
            data = screenUIData,
            isEdit = true,
        ),
        state = rememberCommonScreenUIState(),
    )
}
