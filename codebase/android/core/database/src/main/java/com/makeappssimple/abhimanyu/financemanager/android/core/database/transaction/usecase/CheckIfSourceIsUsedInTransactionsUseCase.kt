package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository

interface CheckIfSourceIsUsedInTransactionsUseCase {
    suspend operator fun invoke(
        sourceId: Int,
    ): Boolean
}

class CheckIfSourceIsUsedInTransactionsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : CheckIfSourceIsUsedInTransactionsUseCase {
    override suspend operator fun invoke(
        sourceId: Int,
    ): Boolean {
        return transactionRepository.checkIfSourceIsUsedInTransactions(
            sourceId = sourceId,
        )
    }
}
