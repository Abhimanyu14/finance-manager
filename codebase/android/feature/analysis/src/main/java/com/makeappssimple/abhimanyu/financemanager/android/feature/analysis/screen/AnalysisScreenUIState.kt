package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.rememberTextMeasurer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.Filter
import java.time.LocalDate

@Stable
class AnalysisScreenUIState(
    data: AnalysisScreenUIData,
    textMeasurer: TextMeasurer,
    val analysisBottomSheetType: AnalysisBottomSheetType,
    val setAnalysisBottomSheetType: (AnalysisBottomSheetType) -> Unit,
) {
    val resetBottomSheetType: () -> Unit = {
        setAnalysisBottomSheetType(AnalysisBottomSheetType.NONE)
    }

    val selectedFilter: Filter = data.selectedFilter
    val selectedTransactionTypeIndex: Int = data.selectedTransactionTypeIndex
    val transactionDataMappedByCategory: List<AnalysisListItemData> =
        data.transactionDataMappedByCategory
    val transactionTypesChipUIData: List<ChipUIData> = data.transactionTypesChipUIData
    val defaultMaxLocalDate: LocalDate = data.defaultMaxLocalDate
    val defaultMinLocalDate: LocalDate = data.defaultMinLocalDate
    val startOfMonthLocalDate: LocalDate = data.startOfMonthLocalDate
    val startOfYearLocalDate: LocalDate = data.startOfYearLocalDate
    val currentTimeMillis: Long = data.currentTimeMillis

    val maxAmountTextWidth: Int = if (transactionDataMappedByCategory.isEmpty()) {
        0
    } else {
        transactionDataMappedByCategory.maxOf {
            textMeasurer.measure(it.amountText).size.width
        }
    }
}

@Composable
fun rememberAnalysisScreenUIState(
    data: AnalysisScreenUIData,
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
