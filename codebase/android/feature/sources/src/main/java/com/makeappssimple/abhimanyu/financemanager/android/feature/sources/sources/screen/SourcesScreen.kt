package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel.SourcesScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel.SourcesScreenViewModelImpl

@Composable
fun SourcesScreen(
    screenViewModel: SourcesScreenViewModel = hiltViewModel<SourcesScreenViewModelImpl>(),
) {
    screenViewModel.myLogger.logError(
        message = "Inside SourcesScreen",
    )

    val screenUIData: MyResult<SourcesScreenUIData>? by screenViewModel.screenUIData.collectAsStateWithLifecycle()

    SourcesScreenUI(
        events = SourcesScreenUIEvents(
            deleteSource = screenViewModel::deleteSource,
            navigateToAddSourceScreen = screenViewModel::navigateToAddSourceScreen,
            navigateToEditSourceScreen = screenViewModel::navigateToEditSourceScreen,
            navigateUp = screenViewModel::navigateUp,
            setDefaultSourceIdInDataStore = screenViewModel::setDefaultSourceIdInDataStore,
        ),
        uiState = rememberSourcesScreenUIState(
            data = screenUIData,
        ),
        state = rememberCommonScreenUIState(),
    )
}
