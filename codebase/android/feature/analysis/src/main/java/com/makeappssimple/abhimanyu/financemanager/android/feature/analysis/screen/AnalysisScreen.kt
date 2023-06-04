package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.AnalysisScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.AnalysisScreenViewModelImpl
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.Filter
import java.time.LocalDate

@Composable
fun AnalysisScreen(
    screenViewModel: AnalysisScreenViewModel = hiltViewModel<AnalysisScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside AnalysisScreen",
    )
    val oldestTransactionLocalDate: LocalDate? by screenViewModel.oldestTransactionLocalDate.collectAsStateWithLifecycle(
        initialValue = LocalDate.MIN,
    )
    val selectedFilter: Filter by screenViewModel.selectedFilter.collectAsStateWithLifecycle()
    val transactionDataMappedByCategory: List<AnalysisListItemData> by screenViewModel.transactionDataMappedByCategory.collectAsStateWithLifecycle()
    val selectedTransactionTypeIndex: Int? by screenViewModel.selectedTransactionTypeIndex.collectAsStateWithLifecycle()

    AnalysisScreenView(
        data = AnalysisScreenViewData(
            selectedFilter = selectedFilter,
            selectedTransactionTypeIndex = selectedTransactionTypeIndex,
            transactionDataMappedByCategory = transactionDataMappedByCategory,
            transactionTypesChipUIData = screenViewModel.transactionTypesChipUIData,
            defaultMaxLocalDate = screenViewModel.currentLocalDate,
            defaultMinLocalDate = oldestTransactionLocalDate ?: LocalDate.MIN,
            startOfMonthLocalDate = screenViewModel.startOfMonthLocalDate,
            startOfYearLocalDate = screenViewModel.startOfYearLocalDate,
            currentTimeMillis = screenViewModel.currentTimeMillis,
        ),
        events = AnalysisScreenViewEvents(
            navigateUp = screenViewModel::navigateUp,
            updateSelectedFilter = screenViewModel::updateSelectedFilter,
            updateSelectedTransactionTypeIndex = screenViewModel::updateSelectedTransactionTypeIndex,
        ),
        state = rememberCommonScreenViewState(),
    )
}
