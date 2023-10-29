package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository

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
