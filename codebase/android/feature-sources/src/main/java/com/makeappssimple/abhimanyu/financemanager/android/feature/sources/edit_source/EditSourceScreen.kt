package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.edit_source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.SourceType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenView
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.screen.AddOrEditSourceScreenViewData
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.viewmodel.AddOrEditSourceScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_or_edit_source.viewmodel.AddOrEditSourceScreenViewModelImpl

@Composable
fun EditSourceScreen(
    screenViewModel: AddOrEditSourceScreenViewModel = hiltViewModel<AddOrEditSourceScreenViewModelImpl>(),
    sourceId: Int?,
) {
    logError(
        message = "Inside EditSourceScreen",
    )
    val selectedSourceTypeIndex: Int by screenViewModel.selectedSourceTypeIndex.collectAsStateWithLifecycle()
    val source: Source? by screenViewModel.source.collectAsStateWithLifecycle(
        initialValue = null,
    )
    val balanceAmountValue: String by screenViewModel.balanceAmountValue.collectAsStateWithLifecycle()
    val name: String by screenViewModel.name.collectAsStateWithLifecycle()

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    AddOrEditSourceScreenView(
        data = AddOrEditSourceScreenViewData(
            autoFocus = source?.type == SourceType.CASH,
            isBalanceAmountTextFieldVisible = true,
            isNameTextFieldVisible = source?.type != SourceType.CASH,
            isSourceTypesRadioGroupVisible = source?.type != SourceType.CASH,
            appBarTitleTextStringResourceId = R.string.screen_edit_source_appbar_title,
            ctaButtonLabelTextStringResourceId = R.string.screen_edit_source_floating_action_button_content_description,
            selectedSourceTypeIndex = selectedSourceTypeIndex,
            sourceId = sourceId,
            sourceTypes = screenViewModel.sourceTypes,
            navigationManager = screenViewModel.navigationManager,
            source = source,
            balanceAmountValue = balanceAmountValue,
            name = name,
            clearBalanceAmountValue = screenViewModel::clearBalanceAmountValue,
            clearName = screenViewModel::clearName,
            isValidSourceData = screenViewModel::isValidSourceData,
            onCtaButtonClick = screenViewModel::updateSource,
            updateBalanceAmountValue = { updatedBalanceAmountValue ->
                screenViewModel.updateBalanceAmountValue(
                    updatedBalanceAmountValue = updatedBalanceAmountValue,
                )
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
