package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface DeleteAllTransactionsUseCase {
    suspend operator fun invoke()
}

class DeleteAllTransactionsUseCaseImpl(
    private val dataStore: MyDataStore,
    private val transactionRepository: TransactionRepository,
) : DeleteAllTransactionsUseCase {
    override suspend operator fun invoke() {
        dataStore.updateLastDataChangeTimestamp()
        return transactionRepository.deleteAllTransactions()
    }
}
