package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel

interface AnalysisScreenViewModel : ScreenViewModel {
    val logger: Logger
    val navigationManager: NavigationManager

    fun navigateUp()
}
