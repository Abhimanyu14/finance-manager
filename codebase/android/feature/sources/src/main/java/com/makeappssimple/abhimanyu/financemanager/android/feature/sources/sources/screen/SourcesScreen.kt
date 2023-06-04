package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.component.listitem.SourcesListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel.SourcesScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel.SourcesScreenViewModelImpl

@Composable
fun SourcesScreen(
    screenViewModel: SourcesScreenViewModel = hiltViewModel<SourcesScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside SourcesScreen",
    )
    val sourcesListItemDataList: List<SourcesListItemData> by screenViewModel.sourcesListItemDataList.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )

    SourcesScreenUI(
        data = SourcesScreenUIData(
            sourcesListItemDataList = sourcesListItemDataList,
        ),
        events = SourcesScreenUIEvents(
            deleteSource = { sourceId ->
                screenViewModel.deleteSource(
                    id = sourceId,
                )
            },
            navigateToAddSourceScreen = {
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.AddSource
                )
            },
            navigateToEditSourceScreen = {
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.EditSource(
                        sourceId = it,
                    )
                )
            },
            navigateUp = {
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.NavigateUp
                )
            },
            setDefaultSourceIdInDataStore = { clickedItemIdValue ->
                screenViewModel.setDefaultSourceIdInDataStore(
                    defaultSourceId = clickedItemIdValue,
                )
            },
        ),
        state = rememberCommonScreenUIState(),
    )
}
