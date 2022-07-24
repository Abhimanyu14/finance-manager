package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.logError
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources.viewmodel.SourcesScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources.viewmodel.SourcesScreenViewModelImpl

@Composable
fun SourcesScreen(
    screenViewModel: SourcesScreenViewModel = hiltViewModel<SourcesScreenViewModelImpl>(),
) {
    logError(
        message = "Inside SourcesScreen",
    )
    val defaultSourceId: Int? by screenViewModel.defaultSourceId.collectAsState(
        initial = null,
    )
    val sources: List<Source> by screenViewModel.sources.collectAsState(
        initial = emptyList(),
    )
    val sourcesIsUsedInTransactions: List<Boolean> by screenViewModel.sourcesIsUsedInTransactions
        .collectAsState(
            initial = emptyList(),
        )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    SourcesScreenView(
        data = SourcesScreenViewData(
            defaultSourceId = defaultSourceId,
            sourcesIsUsedInTransactions = sourcesIsUsedInTransactions,
            sources = sources,
            navigationManager = screenViewModel.navigationManager,
            deleteSource = { sourceId ->
                screenViewModel.deleteSource(
                    id = sourceId,
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
