package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.analysis.AnalysisListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.AnalysisScreenViewModel
import java.time.LocalDate

@Composable
public fun AnalysisScreen(
    screenViewModel: AnalysisScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AnalysisScreen",
    )

    // region view model data
    val selectedTransactionTypeIndex: Int by viewModel.selectedTransactionTypeIndex.collectAsStateWithLifecycle()
    val oldestTransactionLocalDate: LocalDate? by viewModel.oldestTransactionLocalDate.collectAsStateWithLifecycle()
    val transactionDataMappedByCategory: List<AnalysisListItemData> by viewModel.transactionDataMappedByCategory.collectAsStateWithLifecycle()
    val selectedFilter: Filter by viewModel.selectedFilter.collectAsStateWithLifecycle()
    val allTransactionData: List<TransactionData> by viewModel.allTransactionData.collectAsStateWithLifecycle()
    val validTransactionTypes: List<TransactionType> = viewModel.validTransactionTypes
    val transactionTypesChipUIData: List<ChipUIData> = viewModel.transactionTypesChipUIData
    // endregion

    val uiStateAndEvents = rememberAnalysisScreenUIStateAndEvents(
        selectedTransactionTypeIndex = selectedTransactionTypeIndex,
        oldestTransactionLocalDate = oldestTransactionLocalDate,
        transactionDataMappedByCategory = transactionDataMappedByCategory,
        selectedFilter = selectedFilter,
        allTransactionData = allTransactionData,
        validTransactionTypes = validTransactionTypes,
        transactionTypesChipUIData = transactionTypesChipUIData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: AnalysisScreenUIEvent ->
            when (uiEvent) {
                is AnalysisScreenUIEvent.OnBottomSheetDismissed -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AnalysisScreenUIEvent.OnFilterActionButtonClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(AnalysisScreenBottomSheetType.Filters)
                }

                is AnalysisScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AnalysisScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is AnalysisScreenUIEvent.OnAnalysisFilterBottomSheet.PositiveButtonClick -> {
                    viewModel.updateSelectedFilter(
                        updatedSelectedFilter = uiEvent.updatedSelectedFilter,
                    )
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AnalysisScreenUIEvent.OnTransactionTypeChange -> {
                    viewModel.updateSelectedTransactionTypeIndex(
                        updatedSelectedTransactionTypeIndex = uiEvent.updatedSelectedTransactionTypeIndex,
                    )
                }
            }
        }
    }

    AnalysisScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}
