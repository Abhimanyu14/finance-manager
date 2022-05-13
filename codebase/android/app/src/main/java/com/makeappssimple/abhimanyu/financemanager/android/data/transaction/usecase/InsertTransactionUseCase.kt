package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction

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
