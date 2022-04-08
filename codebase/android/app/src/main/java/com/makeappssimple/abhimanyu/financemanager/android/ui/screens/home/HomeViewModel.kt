package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

interface HomeViewModel : BaseViewModel {
    val navigationManager: NavigationManager
    val transactions: Flow<List<Transaction>>
    val sourcesTotalBalanceAmountValue: Flow<Long>
    val sourceFromList: Flow<List<Source?>>
    val sourceToList: Flow<List<Source?>>

    fun deleteTransaction(
        id: Int,
    )
}
