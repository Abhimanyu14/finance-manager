package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.viewmodel.OpenSourceLicensesScreenViewModel

@Composable
public fun OpenSourceLicensesScreen(
    screenViewModel: OpenSourceLicensesScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside OpenSourceLicensesScreen",
    )

    val screenUIData: MyResult<OpenSourceLicensesScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiStateAndEvents = rememberOpenSourceLicensesScreenUIStateAndEvents(
        data = screenUIData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: OpenSourceLicensesScreenUIEvent ->
            when (uiEvent) {
                is OpenSourceLicensesScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is OpenSourceLicensesScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }
            }
        }
    }

    OpenSourceLicensesScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}
