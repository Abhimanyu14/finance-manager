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
    data: MyResult<AnalysisScreenUIData>?,
    private val unwrappedData: AnalysisScreenUIData? = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    },
    textMeasurer: TextMeasurer,
    public val screenBottomSheetType: AnalysisScreenBottomSheetType,
    public val setScreenBottomSheetType: (AnalysisScreenBottomSheetType) -> Unit,
    public val isLoading: Boolean = unwrappedData.isNull(),
    public val selectedFilter: Filter = unwrappedData?.selectedFilter.orEmpty(),
    public val selectedTransactionTypeIndex: Int? = unwrappedData?.selectedTransactionTypeIndex,
    public val transactionDataMappedByCategory: List<AnalysisListItemData> =
        unwrappedData?.transactionDataMappedByCategory.orEmpty(),
    public val transactionTypesChipUIData: List<ChipUIData> =
        unwrappedData?.transactionTypesChipUIData.orEmpty(),
    public val defaultStartLocalDate: LocalDate = unwrappedData?.oldestTransactionLocalDate.orMin(),
    public val defaultEndLocalDate: LocalDate = unwrappedData?.currentLocalDate.orMin(),
    public val startOfMonthLocalDate: LocalDate = unwrappedData?.startOfMonthLocalDate.orMin(),
    public val startOfYearLocalDate: LocalDate = unwrappedData?.startOfYearLocalDate.orMin(),
    public val maxAmountTextWidth: Int = if (transactionDataMappedByCategory.isEmpty()) {
        0
    } else {
        transactionDataMappedByCategory.maxOf {
            textMeasurer.measure(it.amountText).size.width
        }
    },
    public val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(AnalysisScreenBottomSheetType.None)
    },
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
        AnalysisScreenUIState(
            data = data,
            screenBottomSheetType = screenBottomSheetType,
            textMeasurer = textMeasurer,
            setScreenBottomSheetType = setScreenBottomSheetType,
        )
    }
}
