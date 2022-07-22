package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository

interface InsertTransactionsUseCase {
    suspend operator fun invoke(
        vararg transactions: Transaction,
    )
}

class InsertTransactionsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : InsertTransactionsUseCase {
    override suspend operator fun invoke(
        vararg transactions: Transaction,
    ) {
        return transactionRepository.insertTransactions(
            transactions = transactions,
        )
    }
}
