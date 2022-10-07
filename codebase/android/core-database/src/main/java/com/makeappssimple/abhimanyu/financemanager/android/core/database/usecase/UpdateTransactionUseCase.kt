package com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore

interface UpdateTransactionUseCase {
    suspend operator fun invoke(
        originalTransaction: Transaction,
        updatedTransaction: Transaction,
    )
}

class UpdateTransactionUseCaseImpl(
    private val transactionRepository: TransactionRepository,
    private val dataStore: MyDataStore,
) : UpdateTransactionUseCase {
    override suspend operator fun invoke(
        originalTransaction: Transaction,
        updatedTransaction: Transaction,
    ) {
        dataStore.updateLastDataChangeTimestamp()
        transactionRepository.updateTransaction(
            transaction = updatedTransaction,
        )
    }
}
