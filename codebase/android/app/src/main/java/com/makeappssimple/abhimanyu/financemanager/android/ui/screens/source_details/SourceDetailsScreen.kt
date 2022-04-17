package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.source_details

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@ExperimentalMaterialApi
@Composable
fun SourceDetailsScreen(
    screenViewModel: SourceDetailsViewModel = hiltViewModel<SourceDetailsViewModelImpl>(),
    sourceId: Int,
) {
    logError(
        message = "Inside SourceDetailsScreen",
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
        screenViewModel.getSource(
            id = sourceId,
        )
    }

    SourceDetailsScreenView(
        data = SourceDetailsScreenViewData(
            screenViewModel = screenViewModel,
        ),
        state = rememberSourceDetailsScreenViewState(),
    )
}
