package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions

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
fun TransactionsScreen(
    screenViewModel: TransactionsScreenViewModel = hiltViewModel<TransactionsScreenViewModelImpl>(),
) {
    logError(
        message = "Inside TransactionsScreen",
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    TransactionsScreenView(
        data = TransactionsScreenViewData(
            screenViewModel = screenViewModel,
        ),
        state = rememberTransactionsScreenViewState(),
    )
}
