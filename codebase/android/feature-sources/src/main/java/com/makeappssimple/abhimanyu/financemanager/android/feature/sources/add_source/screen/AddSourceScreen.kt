package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_source.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_source.viewmodel.AddSourceScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_source.viewmodel.AddSourceScreenViewModelImpl

@Composable
fun AddSourceScreen(
    screenViewModel: AddSourceScreenViewModel = hiltViewModel<AddSourceScreenViewModelImpl>(),
) {
    logError(
        message = "Inside AddSourceScreen",
    )
    val selectedSourceTypeIndex: Int by screenViewModel.selectedSourceTypeIndex.collectAsStateWithLifecycle()
    val name: String by screenViewModel.name.collectAsStateWithLifecycle()

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    AddSourceScreenView(
        data = AddSourceScreenViewData(
            selectedSourceTypeIndex = selectedSourceTypeIndex,
            sourceTypes = screenViewModel.sourceTypes,
            navigationManager = screenViewModel.navigationManager,
            name = name,
            clearName = {
                screenViewModel.clearName()
            },
            insertSource = {
                screenViewModel.insertSource()
            },
            isValidSourceData = {
                screenViewModel.isValidSourceData()
            },
            updateName = { updatedName ->
                screenViewModel.updateName(
                    updatedName = updatedName,
                )
            },
            updateSelectedSourceTypeIndex = { updatedIndex ->
                screenViewModel.updateSelectedSourceTypeIndex(
                    updatedIndex = updatedIndex,
                )
            },
        ),
        state = rememberCommonScreenViewState(),
    )
}
