package com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transaction.TransactionRepository

interface CheckIfAccountIsUsedInTransactionsUseCase {
    suspend operator fun invoke(
        accountId: Int,
    ): Boolean
}

class CheckIfAccountIsUsedInTransactionsUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : CheckIfAccountIsUsedInTransactionsUseCase {
    override suspend operator fun invoke(
        accountId: Int,
    ): Boolean {
        return transactionRepository.checkIfAccountIsUsedInTransactions(
            accountId = accountId,
        )
    }
}
