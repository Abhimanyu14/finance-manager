package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
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
        dataStore.setLastDataChangeTimestamp()
        transactionRepository.updateTransaction(
            transaction = updatedTransaction,
        )
    }
}
