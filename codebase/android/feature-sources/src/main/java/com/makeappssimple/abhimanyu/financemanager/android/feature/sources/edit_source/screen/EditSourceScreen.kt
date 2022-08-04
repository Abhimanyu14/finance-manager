package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.edit_source.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.edit_source.viewmodel.EditSourceScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.edit_source.viewmodel.EditSourceScreenViewModelImpl

@Composable
fun EditSourceScreen(
    screenViewModel: EditSourceScreenViewModel = hiltViewModel<EditSourceScreenViewModelImpl>(),
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

    EditSourceScreenView(
        data = EditSourceScreenViewData(
            selectedSourceTypeIndex = selectedSourceTypeIndex,
            sourceId = sourceId,
            sourceTypes = screenViewModel.sourceTypes,
            navigationManager = screenViewModel.navigationManager,
            source = source,
            balanceAmountValue = balanceAmountValue,
            name = name,
            clearBalanceAmountValue = {
                screenViewModel.clearBalanceAmountValue()
            },
            clearName = {
                screenViewModel.clearName()
            },
            deleteSource = { id ->
                screenViewModel.deleteSource(
                    id = id,
                )
            },
            insertSource = {
                screenViewModel.insertSource()
            },
            isValidSourceData = {
                screenViewModel.isValidSourceData()
            },
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
            updateSource = {
                screenViewModel.updateSource()
            },
        ),
        state = rememberCommonScreenViewState(),
    )
}
