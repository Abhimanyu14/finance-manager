package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.models.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    transactionRepository: TransactionRepository,
    val navigationManager: NavigationManager,
) : BaseViewModel() {
    val transactions: Flow<List<Transaction>> = transactionRepository.transactions

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }
}
