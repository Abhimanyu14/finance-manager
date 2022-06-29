package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_source.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_source.viewmodel.EditSourceScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_source.viewmodel.EditSourceScreenViewModelImpl
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@Composable
fun EditSourceScreen(
    screenViewModel: EditSourceScreenViewModel = hiltViewModel<EditSourceScreenViewModelImpl>(),
    sourceId: Int?,
) {
    logError(
        message = "Inside EditSourceScreen",
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    EditSourceScreenView(
        data = EditSourceScreenViewData(
            screenViewModel = screenViewModel,
            sourceId = sourceId,
        ),
        state = rememberEditSourceScreenViewState(),
    )
}
