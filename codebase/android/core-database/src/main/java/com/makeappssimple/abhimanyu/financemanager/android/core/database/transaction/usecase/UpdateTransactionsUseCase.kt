package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository

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
