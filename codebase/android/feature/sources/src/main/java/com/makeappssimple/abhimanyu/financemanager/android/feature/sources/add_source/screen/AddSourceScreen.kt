package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_source.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    screenViewModel.logger.logError(
        message = "Inside AddSourceScreen",
    )
    val selectedSourceTypeIndex: Int by screenViewModel.selectedSourceTypeIndex.collectAsStateWithLifecycle()
    val name: TextFieldValue by screenViewModel.name.collectAsStateWithLifecycle()

    AddOrEditSourceScreenView(
        data = AddOrEditSourceScreenViewData(
            isBalanceAmountTextFieldVisible = false,
            isNameTextFieldVisible = true,
            isSourceTypesRadioGroupVisible = true,
            appBarTitleTextStringResourceId = R.string.screen_add_source_appbar_title,
            ctaButtonLabelTextStringResourceId = R.string.screen_add_source_floating_action_button_content_description,
            selectedSourceTypeIndex = selectedSourceTypeIndex,
            sourceTypes = screenViewModel.sourceTypes,
            navigationManager = screenViewModel.navigationManager,
            balanceAmountValue = TextFieldValue(
                text = "",
            ),
            name = name,
            clearBalanceAmountValue = {},
            clearName = screenViewModel::clearName,
            isValidSourceData = screenViewModel::isValidSourceData,
            onCtaButtonClick = screenViewModel::insertSource,
            updateBalanceAmountValue = {},
            updateName = screenViewModel::updateName,
            updateSelectedSourceTypeIndex = screenViewModel::updateSelectedSourceTypeIndex,
        ),
        state = rememberCommonScreenViewState(),
    )
}
