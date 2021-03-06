package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.ScreenViewModel
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
