package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.Flow

interface SourcesScreenViewModel : ScreenViewModel {
    val navigationManager: NavigationManager
    val sources: Flow<List<Source>>
    val sourcesIsUsedInTransactions: Flow<List<Boolean>>
    val defaultSourceId: Flow<Int?>

    fun deleteSource(
        id: Int,
    )

    fun setDefaultSourceIdInDataStore(
        defaultSourceId: Int,
    )
}
