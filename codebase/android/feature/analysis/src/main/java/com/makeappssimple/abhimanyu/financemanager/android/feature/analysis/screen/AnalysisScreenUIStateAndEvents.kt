package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtilImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.analysis.AnalysisListItemData
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDate

@Stable
internal class AnalysisScreenUIStateAndEvents(
    val state: AnalysisScreenUIState,
    val events: AnalysisScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Composable
internal fun rememberAnalysisScreenUIStateAndEvents(
    selectedTransactionTypeIndex: Int,
    oldestTransactionLocalDate: LocalDate?,
    transactionDataMappedByCategory: ImmutableList<AnalysisListItemData>,
    selectedFilter: Filter,
    validTransactionTypes: ImmutableList<TransactionType>,
    transactionTypesChipUIData: ImmutableList<ChipUIData>,
): AnalysisScreenUIStateAndEvents {
    val dateTimeUtil = remember {
        DateTimeUtilImpl()
    }

    // region screen bottom sheet type
    var screenBottomSheetType: AnalysisScreenBottomSheetType by remember {
        mutableStateOf(
            value = AnalysisScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedAnalysisScreenBottomSheetType: AnalysisScreenBottomSheetType ->
            screenBottomSheetType = updatedAnalysisScreenBottomSheetType
        }
    val resetScreenBottomSheetType = {
        setScreenBottomSheetType(AnalysisScreenBottomSheetType.None)
    }
    // endregion

    return remember(
        dateTimeUtil,
        screenBottomSheetType,
        setScreenBottomSheetType,
        selectedFilter,
        selectedTransactionTypeIndex,
        oldestTransactionLocalDate,
        transactionDataMappedByCategory,
        validTransactionTypes,
        transactionTypesChipUIData,
    ) {
        AnalysisScreenUIStateAndEvents(
            state = AnalysisScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != AnalysisScreenBottomSheetType.None,
                selectedFilter = selectedFilter.orEmpty(),
                selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                transactionDataMappedByCategory = transactionDataMappedByCategory,
                transactionTypesChipUIData = transactionTypesChipUIData,
                defaultStartLocalDate = oldestTransactionLocalDate.orMin(),
                defaultEndLocalDate = dateTimeUtil.getCurrentLocalDate(),
                startOfMonthLocalDate = dateTimeUtil.getStartOfMonthLocalDate(),
                startOfYearLocalDate = dateTimeUtil.getStartOfYearLocalDate(),
            ),
            events = AnalysisScreenUIStateEvents(
                resetScreenBottomSheetType = resetScreenBottomSheetType,
                setScreenBottomSheetType = setScreenBottomSheetType,
            ),
        )
    }
}
