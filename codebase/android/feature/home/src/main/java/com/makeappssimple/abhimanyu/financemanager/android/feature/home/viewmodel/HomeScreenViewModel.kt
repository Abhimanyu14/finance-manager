package com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel

import android.net.Uri
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card.OverviewCardAction
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen.HomeScreenUIData
import kotlinx.coroutines.flow.StateFlow

interface HomeScreenViewModel : ScreenViewModel {
    val myLogger: MyLogger
    val screenUIData: StateFlow<MyResult<HomeScreenUIData>?>

    fun backupDataToDocument(
        uri: Uri,
    )

    fun handleOverviewCardAction(
        overviewCardAction: OverviewCardAction,
    )

    fun navigateToAnalysisScreen()

    fun navigateToAddTransactionScreen()

    fun navigateToSettingsScreen()

    fun navigateToSourcesScreen()

    fun navigateToTransactionsScreen()

    fun setOverviewTabSelectionIndex(
        updatedOverviewTabSelectionIndex: Int,
    )
}
