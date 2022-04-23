package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@ExperimentalMaterialApi
@Composable
fun SourcesScreen(
    screenViewModel: SourcesScreenViewModel = hiltViewModel<SourcesScreenViewModelImpl>(),
) {
    logError(
        message = "Inside SourcesScreen",
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    SourcesScreenView(
        data = SourcesScreenViewData(
            screenViewModel = screenViewModel,
        ),
        state = rememberSourcesScreenViewState(),
    )
}
