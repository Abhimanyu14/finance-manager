package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.analysis.AnalysisListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.bottomsheet.AnalysisScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

@Stable
internal data class AnalysisScreenUIState(
    val screenBottomSheetType: AnalysisScreenBottomSheetType = AnalysisScreenBottomSheetType.None,
    val isBottomSheetVisible: Boolean = false,
    val isLoading: Boolean = true,
    val selectedFilter: Filter = Filter(),
    val analysisListItemData: ImmutableList<AnalysisListItemData> = persistentListOf(),
    val transactionTypesChipUIData: ImmutableList<ChipUIData> = persistentListOf(),
    val selectedTransactionTypeIndex: Int = 0,
    val defaultStartLocalDate: LocalDate = LocalDate.MIN,
    val defaultEndLocalDate: LocalDate = LocalDate.MIN,
    val startOfCurrentMonthLocalDate: LocalDate = LocalDate.MIN,
    val startOfCurrentYearLocalDate: LocalDate = LocalDate.MIN,
) : ScreenUIState
