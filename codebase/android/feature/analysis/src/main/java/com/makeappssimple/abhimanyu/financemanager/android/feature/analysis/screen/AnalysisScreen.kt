package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.AnalysisScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.AnalysisScreenViewModelImpl

@Composable
fun AnalysisScreen(
    screenViewModel: AnalysisScreenViewModel = hiltViewModel<AnalysisScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside AnalysisScreen",
    )
    val transactionDataMappedByCategory: List<AnalysisListItemData> by screenViewModel.transactionDataMappedByCategory.collectAsStateWithLifecycle()
    val selectedTransactionTypeIndex: Int? by screenViewModel.selectedTransactionTypeIndex.collectAsStateWithLifecycle()

    AnalysisScreenView(
        data = AnalysisScreenViewData(
            selectedTransactionTypeIndex = selectedTransactionTypeIndex,
            transactionDataMappedByCategory = transactionDataMappedByCategory,
            transactionTypesChipItems = screenViewModel.transactionTypesChipItems,
        ),
        events = AnalysisScreenViewEvents(
            navigateUp = screenViewModel::navigateUp,
            updateSelectedTransactionTypeIndex = screenViewModel::updateSelectedTransactionTypeIndex,
        ),
        state = rememberCommonScreenViewState(),
    )
}
