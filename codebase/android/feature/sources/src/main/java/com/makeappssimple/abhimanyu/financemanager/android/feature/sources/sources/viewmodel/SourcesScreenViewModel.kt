package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.screen.SourcesScreenUIData
import kotlinx.coroutines.flow.StateFlow

interface SourcesScreenViewModel : ScreenViewModel {
    val myLogger: MyLogger
    val screenUIData: StateFlow<MyResult<SourcesScreenUIData>?>

    fun deleteSource(
        id: Int,
    )

    fun navigateToAddSourceScreen()

    fun navigateToEditSourceScreen(
        sourceId: Int,
    )

    fun navigateUp()

    fun setDefaultSourceIdInDataStore(
        defaultSourceId: Int,
    )
}
