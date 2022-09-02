package com.makeappssimple.abhimanyu.financemanager.android.feature.home.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionDetail
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import kotlinx.coroutines.flow.Flow

interface HomeScreenViewModel : ScreenViewModel {
    val navigationManager: NavigationManager
    val homeListItemViewData: Flow<List<TransactionDetail>>

    fun deleteTransaction(
        id: Int,
    )
}
