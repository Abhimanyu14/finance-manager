package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.event.OpenSourceLicensesScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.state.OpenSourceLicensesScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.viewmodel.OpenSourceLicensesScreenViewModel

@Composable
public fun OpenSourceLicensesScreen(
    screenViewModel: OpenSourceLicensesScreenViewModel = hiltViewModel(),
) {
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside OpenSourceLicensesScreen",
    )

    val uiStateAndStateEvents: OpenSourceLicensesScreenUIStateAndStateEvents by screenViewModel.uiStateAndStateEvents.collectAsStateWithLifecycle()

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    val screenUIEventHandler = remember(
        key1 = uiStateAndStateEvents,
    ) {
        OpenSourceLicensesScreenUIEventHandler(
            uiStateAndStateEvents = uiStateAndStateEvents,
        )
    }

    OpenSourceLicensesScreenUI(
        uiState = uiStateAndStateEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
