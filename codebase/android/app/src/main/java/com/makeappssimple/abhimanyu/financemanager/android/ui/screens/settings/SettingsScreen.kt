package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.settings

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun SettingsScreen(
    screenViewModel: SettingsViewModel = hiltViewModel<SettingsViewModelImpl>(),
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
