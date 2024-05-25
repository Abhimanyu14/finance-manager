package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.rememberTextMeasurer
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtilImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.analysis.AnalysisListItemData
import java.time.LocalDate

@Stable
internal class AnalysisScreenUIStateAndEvents(
    val state: AnalysisScreenUIState,
    val events: AnalysisScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Stable
internal class AnalysisScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setScreenBottomSheetType: (AnalysisScreenBottomSheetType) -> Unit,
) : ScreenUIStateEvents

@Composable
internal fun rememberAnalysisScreenUIStateAndEvents(
    selectedTransactionTypeIndex: Int,
    oldestTransactionLocalDate: LocalDate?,
    transactionDataMappedByCategory: List<AnalysisListItemData>,
    selectedFilter: Filter,
    allTransactionData: List<TransactionData>,
    validTransactionTypes: List<TransactionType>,
    transactionTypesChipUIData: List<ChipUIData>,
): AnalysisScreenUIStateAndEvents {
    val textMeasurer: TextMeasurer = rememberTextMeasurer()
    var screenBottomSheetType: AnalysisScreenBottomSheetType by remember {
        mutableStateOf(
            value = AnalysisScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedAnalysisScreenBottomSheetType: AnalysisScreenBottomSheetType ->
            screenBottomSheetType = updatedAnalysisScreenBottomSheetType
        }
    val dateTimeUtil = remember {
        DateTimeUtilImpl()
    }

    return remember(
        screenBottomSheetType,
        textMeasurer,
        setScreenBottomSheetType,
        selectedTransactionTypeIndex,
        oldestTransactionLocalDate,
        transactionDataMappedByCategory,
        selectedFilter,
        allTransactionData,
        validTransactionTypes,
        transactionTypesChipUIData,
    ) {
        AnalysisScreenUIStateAndEvents(
            state = AnalysisScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != AnalysisScreenBottomSheetType.None,
                selectedFilter = selectedFilter.orEmpty(),
                maxAmountTextWidth = if (transactionDataMappedByCategory.isEmpty()) {
                    0
                } else {
                    transactionDataMappedByCategory.maxOf {
                        textMeasurer.measure(it.amountText).size.width
                    }
                },
                selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                transactionDataMappedByCategory = transactionDataMappedByCategory,
                transactionTypesChipUIData = transactionTypesChipUIData,
                defaultStartLocalDate = oldestTransactionLocalDate.orMin(),
                defaultEndLocalDate = dateTimeUtil.getCurrentLocalDate(),
                startOfMonthLocalDate = dateTimeUtil.getStartOfMonthLocalDate(),
                startOfYearLocalDate = dateTimeUtil.getStartOfYearLocalDate(),
            ),
            events = AnalysisScreenUIStateEvents(
                resetScreenBottomSheetType = {
                    setScreenBottomSheetType(AnalysisScreenBottomSheetType.None)
                },
                setScreenBottomSheetType = setScreenBottomSheetType,
            ),
        )
    }
}
