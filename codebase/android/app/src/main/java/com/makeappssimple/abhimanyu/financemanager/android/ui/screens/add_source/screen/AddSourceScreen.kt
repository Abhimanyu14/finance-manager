package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_source.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_source.viewmodel.AddSourceScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_source.viewmodel.AddSourceScreenViewModelImpl
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@OptIn(
    ExperimentalComposeUiApi::class,
)
@Composable
fun AddSourceScreen(
    screenViewModel: AddSourceScreenViewModel = hiltViewModel<AddSourceScreenViewModelImpl>(),
) {
    logError(
        message = "Inside AddSourceScreen",
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    AddSourceScreenView(
        data = AddSourceScreenViewData(
            screenViewModel = screenViewModel,
        ),
        state = rememberAddSourceScreenViewState(),
    )
}
