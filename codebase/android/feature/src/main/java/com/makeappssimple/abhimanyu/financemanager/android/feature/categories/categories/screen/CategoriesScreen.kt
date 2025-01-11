package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalLogKit
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.event.CategoriesScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.state.CategoriesScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.state.CategoriesScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel.CategoriesScreenViewModel

@Composable
public fun CategoriesScreen(
    screenViewModel: CategoriesScreenViewModel = hiltViewModel(),
) {
    val myLogger = LocalLogKit.current
    myLogger.logInfo(
        message = "Inside CategoriesScreen",
    )

    val uiState: CategoriesScreenUIState by screenViewModel.uiState.collectAsStateWithLifecycle()
    val uiStateEvents: CategoriesScreenUIStateEvents = screenViewModel.uiStateEvents

    val screenUIEventHandler = remember(
        key1 = uiStateEvents,
    ) {
        CategoriesScreenUIEventHandler(
            uiStateEvents = uiStateEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.initViewModel()
    }

    CategoriesScreenUI(
        uiState = uiState,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
