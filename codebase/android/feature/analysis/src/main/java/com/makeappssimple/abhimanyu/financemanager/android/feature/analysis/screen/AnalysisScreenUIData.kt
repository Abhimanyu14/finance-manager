package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.Filter
import java.time.LocalDate

@Immutable
data class AnalysisScreenUIData(
    val selectedFilter: Filter = Filter(),
    val selectedTransactionTypeIndex: Int = 0,
    val transactionDataMappedByCategory: List<AnalysisListItemData> = emptyList(),
    val transactionTypesChipUIData: List<ChipUIData> = emptyList(),
    val currentLocalDate: LocalDate = LocalDate.MIN,
    val oldestTransactionLocalDate: LocalDate = LocalDate.MIN,
    val startOfMonthLocalDate: LocalDate = LocalDate.MIN,
    val startOfYearLocalDate: LocalDate = LocalDate.MIN,
) : ScreenUIData
