package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.AnalysisScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.AnalysisScreenViewModelImpl

@Composable
public fun AnalysisScreen(
    screenViewModel: AnalysisScreenViewModel = hiltViewModel<AnalysisScreenViewModelImpl>(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AnalysisScreen",
    )

    val screenUIData: MyResult<AnalysisScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiState = rememberAnalysisScreenUIState(
        data = screenUIData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiState,
    ) {
        { uiEvent: AnalysisScreenUIEvent ->
            @Suppress("UNUSED_EXPRESSION")
            when (uiEvent) {
                else -> {
                    viewModel.handleUIEvent(
                        uiEvent = uiEvent,
                    )
                }
            }
        }
    }

    AnalysisScreenUI(
        uiState = uiState,
        handleUIEvent = handleUIEvent,
    )
}
