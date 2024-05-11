package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.rememberTextMeasurer
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

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
    data: MyResult<AnalysisScreenUIData>?,
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

    return remember(
        data,
        screenBottomSheetType,
        textMeasurer,
        setScreenBottomSheetType,
    ) {
        val unwrappedData: AnalysisScreenUIData? = when (data) {
            is MyResult.Success -> {
                data.data
            }

            else -> {
                null
            }
        }
        val transactionDataMappedByCategory =
            unwrappedData?.transactionDataMappedByCategory.orEmpty()

        // TODO(Abhi): Can be reordered to match the class ordering
        AnalysisScreenUIStateAndEvents(
            state = AnalysisScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isBottomSheetVisible = screenBottomSheetType != AnalysisScreenBottomSheetType.None,
                selectedFilter = unwrappedData?.selectedFilter.orEmpty(),
                maxAmountTextWidth = if (transactionDataMappedByCategory.isEmpty()) {
                    0
                } else {
                    transactionDataMappedByCategory.maxOf {
                        textMeasurer.measure(it.amountText).size.width
                    }
                },
                selectedTransactionTypeIndex = unwrappedData?.selectedTransactionTypeIndex,
                transactionDataMappedByCategory = unwrappedData?.transactionDataMappedByCategory.orEmpty(),
                transactionTypesChipUIData = unwrappedData?.transactionTypesChipUIData.orEmpty(),
                defaultStartLocalDate = unwrappedData?.oldestTransactionLocalDate.orMin(),
                defaultEndLocalDate = unwrappedData?.currentLocalDate.orMin(),
                startOfMonthLocalDate = unwrappedData?.startOfMonthLocalDate.orMin(),
                startOfYearLocalDate = unwrappedData?.startOfYearLocalDate.orMin(),
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
