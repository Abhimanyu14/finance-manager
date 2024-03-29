package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
class AnalysisScreenUIState(
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
    val screenBottomSheetType: AnalysisScreenBottomSheetType,
    val setScreenBottomSheetType: (AnalysisScreenBottomSheetType) -> Unit,
    val isLoading: Boolean = unwrappedData.isNull(),
    val selectedFilter: Filter = unwrappedData?.selectedFilter.orEmpty(),
    val selectedTransactionTypeIndex: Int? = unwrappedData?.selectedTransactionTypeIndex,
    val transactionDataMappedByCategory: List<AnalysisListItemData> =
        unwrappedData?.transactionDataMappedByCategory.orEmpty(),
    val transactionTypesChipUIData: List<ChipUIData> =
        unwrappedData?.transactionTypesChipUIData.orEmpty(),
    val defaultStartLocalDate: LocalDate = unwrappedData?.oldestTransactionLocalDate.orMin(),
    val defaultEndLocalDate: LocalDate = unwrappedData?.currentLocalDate.orMin(),
    val startOfMonthLocalDate: LocalDate = unwrappedData?.startOfMonthLocalDate.orMin(),
    val startOfYearLocalDate: LocalDate = unwrappedData?.startOfYearLocalDate.orMin(),
    val maxAmountTextWidth: Int = if (transactionDataMappedByCategory.isEmpty()) {
        0
    } else {
        transactionDataMappedByCategory.maxOf {
            textMeasurer.measure(it.amountText).size.width
        }
    },
    val resetScreenBottomSheetType: () -> Unit = {
        setScreenBottomSheetType(AnalysisScreenBottomSheetType.NONE)
    },
) : ScreenUIState

@Composable
fun rememberAnalysisScreenUIState(
    data: MyResult<AnalysisScreenUIData>?,
): AnalysisScreenUIState {
    val (screenBottomSheetType, setScreenBottomSheetType) = remember {
        mutableStateOf(
            value = AnalysisScreenBottomSheetType.NONE,
        )
    }
    val textMeasurer: TextMeasurer = rememberTextMeasurer()

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
