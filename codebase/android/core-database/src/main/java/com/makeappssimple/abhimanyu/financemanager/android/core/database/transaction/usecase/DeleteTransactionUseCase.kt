package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository

interface DeleteTransactionUseCase {
    suspend operator fun invoke(
        id: Int,
    )
}

class DeleteTransactionUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : DeleteTransactionUseCase {
    override suspend operator fun invoke(
        id: Int,
    ) {
        return transactionRepository.deleteTransaction(
            id = id,
        )
    }
}
