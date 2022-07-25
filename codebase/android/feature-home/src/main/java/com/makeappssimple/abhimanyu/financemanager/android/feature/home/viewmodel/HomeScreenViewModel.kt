package com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.components.HomeListItemViewData
import kotlinx.coroutines.flow.Flow

interface HomeScreenViewModel : ScreenViewModel {
    val navigationManager: NavigationManager
    val homeListItemViewData: Flow<List<HomeListItemViewData>>

    fun deleteTransaction(
        id: Int,
    )
}
