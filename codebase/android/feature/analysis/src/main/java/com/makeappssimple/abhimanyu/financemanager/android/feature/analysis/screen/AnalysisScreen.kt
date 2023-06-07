package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.AnalysisScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.AnalysisScreenViewModelImpl

@Composable
fun AnalysisScreen(
    screenViewModel: AnalysisScreenViewModel = hiltViewModel<AnalysisScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside AnalysisScreen",
    )
    val screenUIData: AnalysisScreenUIData? by screenViewModel.screenUIData.collectAsStateWithLifecycle()

    AnalysisScreenUI(
        data = screenUIData ?: AnalysisScreenUIData(),
        events = AnalysisScreenUIEvents(
            navigateUp = screenViewModel::navigateUp,
            updateSelectedFilter = screenViewModel::updateSelectedFilter,
            updateSelectedTransactionTypeIndex = screenViewModel::updateSelectedTransactionTypeIndex,
        ),
        state = rememberCommonScreenUIState(),
    )
}
