package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.data.local.source.SourceRepository
import com.makeappssimple.abhimanyu.financemanager.android.data.local.transaction.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.models.Source
import com.makeappssimple.abhimanyu.financemanager.android.models.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val navigationManager: NavigationManager,
    private val sourceRepository: SourceRepository,
    private val transactionRepository: TransactionRepository,
) : BaseViewModel() {
    val transactions: Flow<List<Transaction>> = transactionRepository.transactions

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    fun deleteTransaction(
        id: Int,
    ) {
        viewModelScope.launch(
            context = Dispatchers.IO,
        ) {
            transactionRepository.deleteTransaction(
                id = id,
            )
        }
    }

    fun getSource(
        id: Int,
    ): Flow<Source> {
        return sourceRepository.getSource(
            id = id,
        )
    }
}
