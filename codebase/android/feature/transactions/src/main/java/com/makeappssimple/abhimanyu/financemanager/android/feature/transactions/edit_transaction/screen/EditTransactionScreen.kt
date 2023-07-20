package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenUI
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenUIEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.rememberAddOrEditTransactionScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel.AddOrEditTransactionScreenViewModelImpl

@Composable
fun EditTransactionScreen(
    screenViewModel: AddOrEditTransactionScreenViewModel = hiltViewModel<AddOrEditTransactionScreenViewModelImpl>(),
) {
    screenViewModel.myLogger.logError(
        message = "Inside EditTransactionScreen",
    )

    val screenUIData: MyResult<AddOrEditTransactionScreenUIData>? by screenViewModel.screenUIData.collectAsStateWithLifecycle()

    AddOrEditTransactionScreenUI(
        events = AddOrEditTransactionScreenUIEvents(
            clearAmount = screenViewModel::clearAmount,
            clearDescription = screenViewModel::clearDescription,
            clearTitle = screenViewModel::clearTitle,
            navigateUp = screenViewModel::navigateUp,
            onCtaButtonClick = screenViewModel::updateTransaction,
            updateAmount = screenViewModel::updateAmount,
            updateCategory = screenViewModel::updateCategory,
            updateDescription = screenViewModel::updateDescription,
            updateSelectedTransactionForIndex = screenViewModel::updateSelectedTransactionForIndex,
            updateSelectedTransactionTypeIndex = screenViewModel::updateSelectedTransactionTypeIndex,
            updateAccountFrom = screenViewModel::updateAccountFrom,
            updateAccountTo = screenViewModel::updateAccountTo,
            updateTitle = screenViewModel::updateTitle,
            updateTransactionDate = screenViewModel::updateTransactionDate,
            updateTransactionTime = screenViewModel::updateTransactionTime,
        ),
        uiState = rememberAddOrEditTransactionScreenUIState(
            data = screenUIData,
            isEdit = true,
        ),
        state = rememberCommonScreenUIState(),
    )
}
