package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.screen.SourcesScreenUIData
import kotlinx.coroutines.flow.StateFlow

interface SourcesScreenViewModel : ScreenViewModel {
    val logger: Logger
    val navigationManager: NavigationManager
    val screenUIData: StateFlow<SourcesScreenUIData?>

    fun deleteSource(
        id: Int,
    )

    fun setDefaultSourceIdInDataStore(
        defaultSourceId: Int,
    )
}
