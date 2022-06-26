package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction

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
fun AddTransactionScreen(
    screenViewModel: AddTransactionScreenViewModel = hiltViewModel<AddTransactionScreenViewModelImpl>(),
) {
    logError(
        message = "Inside AddTransactionScreen",
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    AddTransactionScreenView(
        data = AddTransactionScreenViewData(
            screenViewModel = screenViewModel,
        ),
        state = rememberAddTransactionScreenViewState(),
    )
}
