package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenView
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenViewData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.viewmodel.AddOrEditSourceScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.viewmodel.AddOrEditSourceScreenViewModelImpl

@Composable
fun AddSourceScreen(
    screenViewModel: AddOrEditSourceScreenViewModel = hiltViewModel<AddOrEditSourceScreenViewModelImpl>(),
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

    AddOrEditSourceScreenView(
        data = AddOrEditSourceScreenViewData(
            autoFocus = true,
            isBalanceAmountTextFieldVisible = false,
            isNameTextFieldVisible = true,
            isSourceTypesRadioGroupVisible = true,
            appBarTitleTextStringResourceId = R.string.screen_add_source_appbar_title,
            ctaButtonLabelTextStringResourceId = R.string.screen_add_source_floating_action_button_content_description,
            selectedSourceTypeIndex = selectedSourceTypeIndex,
            sourceTypes = screenViewModel.sourceTypes,
            navigationManager = screenViewModel.navigationManager,
            balanceAmountValue = "",
            name = name,
            clearBalanceAmountValue = {},
            clearName = screenViewModel::clearName,
            isValidSourceData = screenViewModel::isValidSourceData,
            onCtaButtonClick = screenViewModel::insertSource,
            updateBalanceAmountValue = {},
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
