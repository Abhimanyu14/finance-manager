package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseScreenViewModel
import kotlinx.coroutines.flow.Flow

interface HomeScreenViewModel : BaseScreenViewModel {
    val navigationManager: NavigationManager
    val homeListItemViewData: Flow<List<HomeListItemViewData>>

    fun deleteTransaction(
        id: Int,
    )
}
