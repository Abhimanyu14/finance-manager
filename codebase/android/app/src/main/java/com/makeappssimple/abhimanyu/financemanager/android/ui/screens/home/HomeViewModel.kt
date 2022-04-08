package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

interface HomeViewModel : BaseViewModel {
    val navigationManager: NavigationManager
    val sourcesTotalBalanceAmountValue: Flow<Long>
    val homeListItemViewData: Flow<List<HomeListItemViewData>>

    fun deleteTransaction(
        id: Int,
    )
}
