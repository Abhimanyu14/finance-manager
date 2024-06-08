package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
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

    val uiStateAndEvents = rememberOpenSourceLicensesScreenUIStateAndEvents()
    val screenUIEventHandler = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        OpenSourceLicensesScreenUIEventHandler(
            viewModel = viewModel,
            uiStateAndEvents = uiStateAndEvents,
        )
    }

    OpenSourceLicensesScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
