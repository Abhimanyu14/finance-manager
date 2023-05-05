package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface DeleteAllTransactionsUseCase {
    suspend operator fun invoke()
}

class DeleteAllTransactionsUseCaseImpl(
    private val dataStore: MyDataStore,
    private val transactionRepository: TransactionRepository,
) : DeleteAllTransactionsUseCase {
    override suspend operator fun invoke() {
        dataStore.setLastDataChangeTimestamp()
        return transactionRepository.deleteAllTransactions()
    }
}
