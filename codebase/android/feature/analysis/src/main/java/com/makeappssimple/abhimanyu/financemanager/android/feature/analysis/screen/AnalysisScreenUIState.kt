package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.analysis.AnalysisListItemData
import java.time.LocalDate

@Stable
internal data class AnalysisScreenUIState(
    val screenBottomSheetType: AnalysisScreenBottomSheetType,
    val isBottomSheetVisible: Boolean,
    val selectedFilter: Filter,
    val maxAmountTextWidth: Int,
    val selectedTransactionTypeIndex: Int?,
    val transactionDataMappedByCategory: List<AnalysisListItemData>,
    val transactionTypesChipUIData: List<ChipUIData>,
    val defaultStartLocalDate: LocalDate,
    val defaultEndLocalDate: LocalDate,
    val startOfMonthLocalDate: LocalDate,
    val startOfYearLocalDate: LocalDate,
) : ScreenUIState
