package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction

interface UpdateTransactionsUseCase {
    suspend operator fun invoke(
        vararg transactions: Transaction,
    )
}

class UpdateTransactionsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : UpdateTransactionsUseCase {
    override suspend operator fun invoke(
        vararg transactions: Transaction,
    ) {
        return transactionRepository.updateTransactions(
            transactions = transactions,
        )
    }
}
