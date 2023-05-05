package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface InsertTransactionsUseCase {
    suspend operator fun invoke(
        vararg transactions: Transaction,
    )
}

class InsertTransactionsUseCaseImpl(
    private val dataStore: MyDataStore,
    private val transactionRepository: TransactionRepository,
) : InsertTransactionsUseCase {
    override suspend operator fun invoke(
        vararg transactions: Transaction,
    ) {
        dataStore.setLastDataChangeTimestamp()
        return transactionRepository.insertTransactions(
            transactions = transactions,
        )
    }
}
