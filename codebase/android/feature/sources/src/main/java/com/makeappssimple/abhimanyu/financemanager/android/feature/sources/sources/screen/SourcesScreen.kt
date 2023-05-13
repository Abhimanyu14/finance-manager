package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel.SourcesScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel.SourcesScreenViewModelImpl

@Composable
fun SourcesScreen(
    screenViewModel: SourcesScreenViewModel = hiltViewModel<SourcesScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside SourcesScreen",
    )
    val defaultSourceId: Int? by screenViewModel.defaultSourceId.collectAsStateWithLifecycle(
        initialValue = null,
    )
    val sources: List<Source> by screenViewModel.sources.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val sourcesIsUsedInTransactions: List<Boolean> by screenViewModel.sourcesIsUsedInTransactions
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
        )

    SourcesScreenView(
        data = SourcesScreenViewData(
            defaultSourceId = defaultSourceId,
            sourcesIsUsedInTransactions = sourcesIsUsedInTransactions,
            sources = sources,
        ),
        events = SourcesScreenViewEvents(
            deleteSource = { sourceId ->
                screenViewModel.deleteSource(
                    source = sourceId,
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
        state = rememberCommonScreenViewState(),
    )
}
