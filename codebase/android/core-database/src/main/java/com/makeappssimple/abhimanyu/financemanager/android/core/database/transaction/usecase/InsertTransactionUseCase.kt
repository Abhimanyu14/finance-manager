package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository

interface InsertTransactionUseCase {
    suspend operator fun invoke(
        transaction: Transaction,
    )
}

class InsertTransactionUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : InsertTransactionUseCase {
    override suspend operator fun invoke(
        transaction: Transaction,
    ) {
        return transactionRepository.insertTransaction(
            transaction = transaction,
        )
    }
}
