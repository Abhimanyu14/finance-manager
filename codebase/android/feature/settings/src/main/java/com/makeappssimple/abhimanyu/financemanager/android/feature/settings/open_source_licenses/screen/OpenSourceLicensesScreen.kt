package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
    val viewModel = remember {
        screenViewModel
    }
    viewModel.myLogger.logError(
        message = "Inside OpenSourceLicensesScreen",
    )

    val screenUIData: MyResult<OpenSourceLicensesScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val handleUIEvents = remember(
        key1 = viewModel,
    ) {
        { uiEvent: OpenSourceLicensesScreenUIEvent ->
            when (uiEvent) {
                else -> {
                    viewModel.handleUIEvents(
                        uiEvent = uiEvent,
                    )
                }
            }
        }
    }

    OpenSourceLicensesScreenUI(
        uiState = rememberOpenSourceLicensesScreenUIState(
            data = screenUIData,
        ),
        state = rememberCommonScreenUIState(),
        handleUIEvents = handleUIEvents,
    )
}
