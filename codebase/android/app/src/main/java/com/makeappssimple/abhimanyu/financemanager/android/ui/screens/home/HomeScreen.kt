package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

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
fun HomeScreen(
    screenViewModel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModelImpl>(),
) {
    logError(
        message = "Inside HomeScreen",
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    HomeScreenView(
        data = HomeScreenViewData(
            screenViewModel = screenViewModel,
        ),
        state = rememberHomeScreenViewState(),
    )
}
