package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction.viewmodel.EditTransactionScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction.viewmodel.EditTransactionScreenViewModelImpl
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@Composable
fun EditTransactionScreen(
    screenViewModel: EditTransactionScreenViewModel = hiltViewModel<EditTransactionScreenViewModelImpl>(),
    transactionId: Int?,
) {
    logError(
        message = "Inside EditTransactionScreen",
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    EditTransactionScreenView(
        data = EditTransactionScreenViewData(
            screenViewModel = screenViewModel,
            transactionId = transactionId,
        ),
        state = rememberEditTransactionScreenViewState(),
    )
}
