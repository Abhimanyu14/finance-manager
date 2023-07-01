package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen.AnalysisScreenUIData
import kotlinx.coroutines.flow.StateFlow

interface AnalysisScreenViewModel : ScreenViewModel {
    val myLogger: MyLogger
    val navigationManager: NavigationManager
    val screenUIData: StateFlow<MyResult<AnalysisScreenUIData>?>

    fun navigateUp()

    fun updateSelectedFilter(
        updatedSelectedFilter: Filter,
    )

    fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    )
}
