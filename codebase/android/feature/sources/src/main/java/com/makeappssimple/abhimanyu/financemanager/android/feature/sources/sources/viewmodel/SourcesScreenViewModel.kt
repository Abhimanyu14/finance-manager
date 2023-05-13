package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.Flow

interface SourcesScreenViewModel : ScreenViewModel {
    val logger: Logger
    val navigationManager: NavigationManager
    val sources: Flow<List<Source>>
    val sourcesIsUsedInTransactions: Flow<List<Boolean>>
    val defaultSourceId: Flow<Int?>

    fun deleteSource(
        source: Source,
    )

    fun setDefaultSourceIdInDataStore(
        defaultSourceId: Int,
    )
}
