package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.analysis.AnalysisListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.AnalysisScreenViewModel
import kotlinx.collections.immutable.ImmutableList
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
    val transactionDataMappedByCategory: ImmutableList<AnalysisListItemData> by viewModel.transactionDataMappedByCategory.collectAsStateWithLifecycle()
    val selectedFilter: Filter by viewModel.selectedFilter.collectAsStateWithLifecycle()
    val validTransactionTypes: ImmutableList<TransactionType> = viewModel.validTransactionTypes
    val transactionTypesChipUIData: ImmutableList<ChipUIData> = viewModel.transactionTypesChipUIData
    // endregion

    val uiStateAndEvents = rememberAnalysisScreenUIStateAndEvents(
        selectedTransactionTypeIndex = selectedTransactionTypeIndex,
        oldestTransactionLocalDate = oldestTransactionLocalDate,
        transactionDataMappedByCategory = transactionDataMappedByCategory,
        selectedFilter = selectedFilter,
        validTransactionTypes = validTransactionTypes,
        transactionTypesChipUIData = transactionTypesChipUIData,
    )
    val screenUIEventHandler = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        AnalysisScreenUIEventHandler(
            viewModel = viewModel,
            uiStateAndEvents = uiStateAndEvents,
        )
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        viewModel.initViewModel()
    }

    AnalysisScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = screenUIEventHandler::handleUIEvent,
    )
}
