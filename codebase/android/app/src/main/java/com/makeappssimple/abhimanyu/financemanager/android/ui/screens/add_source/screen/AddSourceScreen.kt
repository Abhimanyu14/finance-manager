package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_source.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_source.viewmodel.AddSourceScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_source.viewmodel.AddSourceScreenViewModelImpl
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@Composable
fun AddSourceScreen(
    screenViewModel: AddSourceScreenViewModel = hiltViewModel<AddSourceScreenViewModelImpl>(),
) {
    logError(
        message = "Inside AddSourceScreen",
    )
    val selectedSourceTypeIndex: Int by screenViewModel.selectedSourceTypeIndex.collectAsState()
    val name: String by screenViewModel.name.collectAsState()

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
