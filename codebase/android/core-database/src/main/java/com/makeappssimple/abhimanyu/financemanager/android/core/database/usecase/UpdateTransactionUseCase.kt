package com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository

interface UpdateTransactionUseCase {
    suspend operator fun invoke(
        originalTransaction: Transaction,
        updatedTransaction: Transaction,
    )
}

class UpdateTransactionUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : UpdateTransactionUseCase {
    override suspend operator fun invoke(
        originalTransaction: Transaction,
        updatedTransaction: Transaction,
    ) {
        transactionRepository.updateTransaction(
            transaction = updatedTransaction,
        )
    }
}
