package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.settings.screen

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.settings.viewmodel.SettingsScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.settings.viewmodel.SettingsScreenViewModelImpl
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@OptIn(
    ExperimentalMaterialApi::class,
)
@Composable
fun SettingsScreen(
    screenViewModel: SettingsScreenViewModel = hiltViewModel<SettingsScreenViewModelImpl>(),
) {
    logError(
        message = "Inside SettingsScreen",
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    SettingsScreenView(
        data = SettingsScreenViewData(
            screenViewModel = screenViewModel,
        ),
        state = rememberSettingsScreenViewState(),
    )
}
