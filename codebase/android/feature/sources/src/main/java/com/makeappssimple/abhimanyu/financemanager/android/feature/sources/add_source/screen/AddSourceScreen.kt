package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_source.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenUI
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenUIErrorData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenUIEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenUIVisibilityData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.viewmodel.AddOrEditSourceScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.viewmodel.AddOrEditSourceScreenViewModelImpl

@Composable
fun AddSourceScreen(
    screenViewModel: AddOrEditSourceScreenViewModel = hiltViewModel<AddOrEditSourceScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside AddSourceScreen",
    )
    val errorData: AddOrEditSourceScreenUIErrorData by screenViewModel.errorData.collectAsStateWithLifecycle()
    val selectedSourceTypeIndex: Int by screenViewModel.selectedSourceTypeIndex.collectAsStateWithLifecycle()
    val name: TextFieldValue by screenViewModel.name.collectAsStateWithLifecycle()

    AddOrEditSourceScreenUI(
        data = AddOrEditSourceScreenUIData(
            visibilityData = AddOrEditSourceScreenUIVisibilityData(
                balanceAmount = false,
                name = true,
                sourceTypes = true,
            ),
            errorData = errorData,
            appBarTitleTextStringResourceId = R.string.screen_add_source_appbar_title,
            ctaButtonLabelTextStringResourceId = R.string.screen_add_source_floating_action_button_content_description,
            selectedSourceTypeIndex = selectedSourceTypeIndex,
            sourceTypes = screenViewModel.sourceTypes,
            balanceAmountValue = TextFieldValue(
                text = "",
            ),
            name = name,
        ),
        events = AddOrEditSourceScreenUIEvents(
            clearBalanceAmountValue = {},
            clearName = screenViewModel::clearName,
            isValidSourceData = screenViewModel::isValidSourceData,
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
        state = rememberCommonScreenUIState(),
    )
}
