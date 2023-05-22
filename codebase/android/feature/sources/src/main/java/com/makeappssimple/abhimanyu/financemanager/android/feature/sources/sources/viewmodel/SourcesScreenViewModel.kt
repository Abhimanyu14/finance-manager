package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.components.listitem.SourcesListItemData
import kotlinx.coroutines.flow.Flow

interface SourcesScreenViewModel : ScreenViewModel {
    val logger: Logger
    val navigationManager: NavigationManager
    val sourcesListItemDataList: Flow<List<SourcesListItemData>>

    fun deleteSource(
        id: Int,
    )

    fun setDefaultSourceIdInDataStore(
        defaultSourceId: Int,
    )
}
