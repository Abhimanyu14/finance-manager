package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home.components.HomeListItemViewData
import kotlinx.coroutines.flow.Flow

interface HomeScreenViewModel : ScreenViewModel {
    val navigationManager: NavigationManager
    val homeListItemViewData: Flow<List<HomeListItemViewData>>

    fun deleteTransaction(
        id: Int,
    )
}
