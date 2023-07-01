package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_source.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenUI
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenUIEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.rememberAddOrEditSourceScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.viewmodel.AddOrEditSourceScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.viewmodel.AddOrEditSourceScreenViewModelImpl

@Composable
fun AddSourceScreen(
    screenViewModel: AddOrEditSourceScreenViewModel = hiltViewModel<AddOrEditSourceScreenViewModelImpl>(),
) {
    screenViewModel.myLogger.logError(
        message = "Inside AddSourceScreen",
    )

    val screenUIData: MyResult<AddOrEditSourceScreenUIData>? by screenViewModel.screenUIData.collectAsStateWithLifecycle()

    AddOrEditSourceScreenUI(
        events = AddOrEditSourceScreenUIEvents(
            clearBalanceAmountValue = {},
            clearName = screenViewModel::clearName,
            navigateUp = {
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.NavigateUp
                )
            },
            onCtaButtonClick = screenViewModel::insertSource,
            updateBalanceAmountValue = {},
            updateName = screenViewModel::updateName,
            updateSelectedSourceTypeIndex = screenViewModel::updateSelectedSourceTypeIndex,
        ),
        uiState = rememberAddOrEditSourceScreenUIState(
            data = screenUIData,
            isEdit = false,
        ),
        state = rememberCommonScreenUIState(),
    )
}
