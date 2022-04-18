package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_source

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@ExperimentalMaterialApi
@Composable
fun EditSourceScreen(
    screenViewModel: EditSourceViewModel = hiltViewModel<EditSourceViewModelImpl>(),
    sourceId: Int,
) {
    logError(
        message = "Inside EditSourceScreen",
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
        screenViewModel.getSource(
            id = sourceId,
        )
    }

    EditSourceScreenView(
        data = EditSourceScreenViewData(
            screenViewModel = screenViewModel,
            sourceId = sourceId,
        ),
        state = rememberEditSourceScreenViewState(),
    )
}
