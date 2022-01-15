package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@Composable
fun AddSourceScreen(
    screenViewModel: AddSourceViewModel = hiltViewModel(),
) {
    logError("Inside AddSourceScreen")

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    AddSourceScreenView(
        data = AddSourceScreenViewData(
            screenViewModel = screenViewModel,
        ),
    )
}
