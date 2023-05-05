package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository

interface CheckIfTransactionForIsUsedInTransactionsUseCase {
    suspend operator fun invoke(
        transactionForId: Int,
    ): Boolean
}

class CheckIfTransactionForIsUsedInTransactionsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.CheckIfTransactionForIsUsedInTransactionsUseCase {
    override suspend operator fun invoke(
        transactionForId: Int,
    ): Boolean {
        return transactionRepository.checkIfTransactionForIsUsedInTransactions(
            transactionForId = transactionForId,
        )
    }
}
