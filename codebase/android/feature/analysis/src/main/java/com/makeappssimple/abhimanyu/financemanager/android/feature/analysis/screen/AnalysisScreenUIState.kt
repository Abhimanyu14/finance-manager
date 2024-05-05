package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.rememberTextMeasurer
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.analysis.AnalysisListItemData
import java.time.LocalDate

@Stable
public class AnalysisScreenUIState(
    public val screenBottomSheetType: AnalysisScreenBottomSheetType,
    public val isLoading: Boolean,
    public val selectedFilter: Filter,
    public val maxAmountTextWidth: Int,
    public val selectedTransactionTypeIndex: Int?,
    public val transactionDataMappedByCategory: List<AnalysisListItemData>,
    public val transactionTypesChipUIData: List<ChipUIData>,
    public val defaultStartLocalDate: LocalDate,
    public val defaultEndLocalDate: LocalDate,
    public val startOfMonthLocalDate: LocalDate,
    public val startOfYearLocalDate: LocalDate,
    public val resetScreenBottomSheetType: () -> Unit,
    public val setScreenBottomSheetType: (AnalysisScreenBottomSheetType) -> Unit,
) : ScreenUIState

@Composable
public fun rememberAnalysisScreenUIState(
    data: MyResult<AnalysisScreenUIData>?,
): AnalysisScreenUIState {
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
        AnalysisScreenUIState(
            screenBottomSheetType = screenBottomSheetType,
            setScreenBottomSheetType = setScreenBottomSheetType,
            isLoading = unwrappedData.isNull(),
            selectedFilter = unwrappedData?.selectedFilter.orEmpty(),
            selectedTransactionTypeIndex = unwrappedData?.selectedTransactionTypeIndex,
            transactionDataMappedByCategory = unwrappedData?.transactionDataMappedByCategory.orEmpty(),
            transactionTypesChipUIData = unwrappedData?.transactionTypesChipUIData.orEmpty(),
            defaultStartLocalDate = unwrappedData?.oldestTransactionLocalDate.orMin(),
            defaultEndLocalDate = unwrappedData?.currentLocalDate.orMin(),
            startOfMonthLocalDate = unwrappedData?.startOfMonthLocalDate.orMin(),
            startOfYearLocalDate = unwrappedData?.startOfYearLocalDate.orMin(),
            maxAmountTextWidth = if (transactionDataMappedByCategory.isEmpty()) {
                0
            } else {
                transactionDataMappedByCategory.maxOf {
                    textMeasurer.measure(it.amountText).size.width
                }
            },
            resetScreenBottomSheetType = {
                setScreenBottomSheetType(AnalysisScreenBottomSheetType.None)
            },
        )
    }
}
