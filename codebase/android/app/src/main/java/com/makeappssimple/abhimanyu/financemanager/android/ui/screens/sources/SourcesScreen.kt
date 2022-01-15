package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@Composable
fun SourcesScreen(
    screenViewModel: SourcesViewModel = hiltViewModel(),
) {
    logError("Inside SourcesScreen")

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    SourcesScreenView(
        data = SourcesScreenViewData(
            screenViewModel = screenViewModel,
        ),
    )
}
