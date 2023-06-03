package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItemData
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

interface AnalysisScreenViewModel : ScreenViewModel {
    val logger: Logger
    val navigationManager: NavigationManager
    val transactionTypesChipUIData: List<ChipUIData>
    val currentLocalDate: LocalDate
    val currentTimeMillis: Long
    val selectedFilter: StateFlow<Filter>
    val transactionDataMappedByCategory: StateFlow<List<AnalysisListItemData>>
    val selectedTransactionTypeIndex: StateFlow<Int>
    val oldestTransactionLocalDate: StateFlow<LocalDate?>

    fun navigateUp()

    fun updateSelectedFilter(
        updatedSelectedFilter: Filter,
    )

    fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    )
}
