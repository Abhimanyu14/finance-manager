package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class,
)
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
