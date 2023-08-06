package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.viewmodel.OpenSourceLicensesScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.viewmodel.OpenSourceLicensesScreenViewModelImpl

@Composable
fun OpenSourceLicensesScreen(
    screenViewModel: OpenSourceLicensesScreenViewModel = hiltViewModel<OpenSourceLicensesScreenViewModelImpl>(),
) {
    screenViewModel.myLogger.logError(
        message = "Inside OpenSourceLicensesScreen",
    )

    val screenUIData: MyResult<OpenSourceLicensesScreenUIData>? by screenViewModel.screenUIData.collectAsStateWithLifecycle()

    OpenSourceLicensesScreenUI(
        events = OpenSourceLicensesScreenUIEvents(
            navigateUp = screenViewModel::navigateUp,
        ),
        uiState = rememberOpenSourceLicensesScreenUIState(
            data = screenUIData,
        ),
        state = rememberCommonScreenUIState(),
    )
}
