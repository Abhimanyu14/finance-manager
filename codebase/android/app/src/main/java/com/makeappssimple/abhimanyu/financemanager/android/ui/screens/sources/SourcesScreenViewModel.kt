package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.sources

import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseScreenViewModel
import kotlinx.coroutines.flow.Flow

interface SourcesScreenViewModel : BaseScreenViewModel {
    val navigationManager: NavigationManager
    val sources: Flow<List<Source>>
    val sourcesIsUsedInTransactions: Flow<List<Boolean>>

    fun deleteSource(
        id: Int,
    )
}
