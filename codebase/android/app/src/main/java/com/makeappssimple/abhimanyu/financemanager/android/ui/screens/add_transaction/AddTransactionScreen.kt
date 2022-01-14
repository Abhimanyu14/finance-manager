package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@ExperimentalMaterial3Api
@Composable
fun AddTransactionScreen(
    screenViewModel: AddTransactionViewModel = hiltViewModel(),
) {
    logError("Inside AddTransactionScreen")

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    AddTransactionScreenView(
        data = AddTransactionScreenViewData(
            screenViewModel = screenViewModel,
        ),
    )
}