package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen.event.AnalysisScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen.state.AnalysisScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.AnalysisScreenViewModel

@Composable
public fun AnalysisScreen(
    screenViewModel: AnalysisScreenViewModel = hiltViewModel(),
) {
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AnalysisScreen",
    )

    val uiStateAndStateEvents: AnalysisScreenUIStateAndStateEvents by screenViewModel.uiStateAndStateEvents.collectAsStateWithLifecycle()

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    val screenUIEventHandler = remember(
        key1 = screenViewModel,
        key2 = uiStateAndStateEvents,
    ) {
        AnalysisScreenUIEventHandler(
            uiStateAndStateEvents = uiStateAndStateEvents,
        )
    }

    AnalysisScreenUI(
        uiState = uiStateAndStateEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
