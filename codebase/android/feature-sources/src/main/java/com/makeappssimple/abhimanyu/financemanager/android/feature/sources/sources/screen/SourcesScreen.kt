package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel.SourcesScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel.SourcesScreenViewModelImpl

@Composable
fun SourcesScreen(
    screenViewModel: SourcesScreenViewModel = hiltViewModel<SourcesScreenViewModelImpl>(),
) {
    logError(
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
                    sourceId,
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
