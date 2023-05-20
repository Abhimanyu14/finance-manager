package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.edit_source.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenView
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenViewData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenViewErrorData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenViewEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenViewVisibilityData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.viewmodel.AddOrEditSourceScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.viewmodel.AddOrEditSourceScreenViewModelImpl

@Composable
fun EditSourceScreen(
    screenViewModel: AddOrEditSourceScreenViewModel = hiltViewModel<AddOrEditSourceScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside EditSourceScreen",
    )
    val errorData: AddOrEditSourceScreenViewErrorData by screenViewModel.errorData.collectAsStateWithLifecycle()
    val selectedSourceTypeIndex: Int by screenViewModel.selectedSourceTypeIndex.collectAsStateWithLifecycle()
    val source: Source? by screenViewModel.originalSource.collectAsStateWithLifecycle(
        initialValue = null,
    )
    val balanceAmountValue: TextFieldValue by screenViewModel.balanceAmountValue.collectAsStateWithLifecycle()
    val name: TextFieldValue by screenViewModel.name.collectAsStateWithLifecycle()

    AddOrEditSourceScreenView(
        data = AddOrEditSourceScreenViewData(
            visibilityData = AddOrEditSourceScreenViewVisibilityData(
                balanceAmount = true,
                name = source?.type != SourceType.CASH,
                sourceTypes = source?.type != SourceType.CASH,
            ),
            errorData = errorData,
            appBarTitleTextStringResourceId = R.string.screen_edit_source_appbar_title,
            ctaButtonLabelTextStringResourceId = R.string.screen_edit_source_floating_action_button_content_description,
            selectedSourceTypeIndex = selectedSourceTypeIndex,
            sourceTypes = screenViewModel.sourceTypes,
            balanceAmountValue = balanceAmountValue,
            name = name,
        ),
        events = AddOrEditSourceScreenViewEvents(
            clearBalanceAmountValue = screenViewModel::clearBalanceAmountValue,
            clearName = screenViewModel::clearName,
            isValidSourceData = screenViewModel::isValidSourceData,
            navigateUp = {
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.NavigateUp
                )
            },
            onCtaButtonClick = screenViewModel::updateSource,
            updateBalanceAmountValue = screenViewModel::updateBalanceAmountValue,
            updateName = screenViewModel::updateName,
            updateSelectedSourceTypeIndex = screenViewModel::updateSelectedSourceTypeIndex,
        ),
        state = rememberCommonScreenViewState(),
    )
}
