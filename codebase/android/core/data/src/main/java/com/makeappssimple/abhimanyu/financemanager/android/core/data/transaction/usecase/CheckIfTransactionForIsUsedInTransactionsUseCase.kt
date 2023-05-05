package com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.repository.TransactionRepository

interface CheckIfTransactionForIsUsedInTransactionsUseCase {
    suspend operator fun invoke(
        transactionForId: Int,
    ): Boolean
}

class CheckIfTransactionForIsUsedInTransactionsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : CheckIfTransactionForIsUsedInTransactionsUseCase {
    override suspend operator fun invoke(
        transactionForId: Int,
    ): Boolean {
        return transactionRepository.checkIfTransactionForIsUsedInTransactions(
            transactionForId = transactionForId,
        )
    }
}
