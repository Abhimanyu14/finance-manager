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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.Filter
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.orEmpty
import java.time.LocalDate

@Stable
class AnalysisScreenUIState(
    data: MyResult<AnalysisScreenUIData>?,
    textMeasurer: TextMeasurer,
    val analysisBottomSheetType: AnalysisBottomSheetType,
    val setAnalysisBottomSheetType: (AnalysisBottomSheetType) -> Unit,
) {
    private val unwrappedData = when (data) {
        is MyResult.Success -> {
            data.data
        }

        else -> {
            null
        }
    }

    val isLoading: Boolean = unwrappedData.isNull()
    val selectedFilter: Filter = unwrappedData?.selectedFilter.orEmpty()
    val selectedTransactionTypeIndex: Int? = unwrappedData?.selectedTransactionTypeIndex
    val transactionDataMappedByCategory: List<AnalysisListItemData> =
        unwrappedData?.transactionDataMappedByCategory.orEmpty()
    val transactionTypesChipUIData: List<ChipUIData> =
        unwrappedData?.transactionTypesChipUIData.orEmpty()
    val defaultStartLocalDate: LocalDate = unwrappedData?.oldestTransactionLocalDate.orMin()
    val defaultEndLocalDate: LocalDate = unwrappedData?.currentLocalDate.orMin()
    val startOfMonthLocalDate: LocalDate = unwrappedData?.startOfMonthLocalDate.orMin()
    val startOfYearLocalDate: LocalDate = unwrappedData?.startOfYearLocalDate.orMin()

    val maxAmountTextWidth: Int = if (transactionDataMappedByCategory.isEmpty()) {
        0
    } else {
        transactionDataMappedByCategory.maxOf {
            textMeasurer.measure(it.amountText).size.width
        }
    }
    val resetBottomSheetType: () -> Unit = {
        setAnalysisBottomSheetType(AnalysisBottomSheetType.NONE)
    }
}

@Composable
fun rememberAnalysisScreenUIState(
    data: MyResult<AnalysisScreenUIData>?,
): AnalysisScreenUIState {
    val (analysisBottomSheetType, setAnalysisBottomSheetType) = remember {
        mutableStateOf(
            value = AnalysisBottomSheetType.NONE,
        )
    }
    val textMeasurer: TextMeasurer = rememberTextMeasurer()

    return remember(
        data,
        analysisBottomSheetType,
        textMeasurer,
        setAnalysisBottomSheetType,
    ) {
        AnalysisScreenUIState(
            data = data,
            analysisBottomSheetType = analysisBottomSheetType,
            textMeasurer = textMeasurer,
            setAnalysisBottomSheetType = setAnalysisBottomSheetType,
        )
    }
}
