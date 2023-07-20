package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository

interface CheckIfAccountIsUsedInTransactionsUseCase {
    suspend operator fun invoke(
        sourceId: Int,
    ): Boolean
}

class CheckIfAccountIsUsedInTransactionsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : CheckIfAccountIsUsedInTransactionsUseCase {
    override suspend operator fun invoke(
        sourceId: Int,
    ): Boolean {
        return transactionRepository.checkIfAccountIsUsedInTransactions(
            sourceId = sourceId,
        )
    }
}
