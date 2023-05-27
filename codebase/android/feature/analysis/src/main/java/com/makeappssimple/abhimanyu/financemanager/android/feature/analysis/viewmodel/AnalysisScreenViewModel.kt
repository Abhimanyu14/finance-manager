package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.ChipItem
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.component.listitem.AnalysisListItemData
import kotlinx.coroutines.flow.StateFlow

interface AnalysisScreenViewModel : ScreenViewModel {
    val logger: Logger
    val navigationManager: NavigationManager
    val transactionTypesChipItems: List<ChipItem>
    val transactionDataMappedByCategory: StateFlow<List<AnalysisListItemData>>
    val selectedTransactionTypeIndex: StateFlow<Int?>

    fun navigateUp()

    fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    )
}
