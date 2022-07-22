package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository.TransactionRepository

interface GetTransactionUseCase {
    suspend operator fun invoke(
        id: Int,
    ): Transaction?
}

class GetTransactionUseCaseImpl(
    private val transactionRepository: TransactionRepository,
) : GetTransactionUseCase {
    override suspend operator fun invoke(
        id: Int,
    ): Transaction? {
        return transactionRepository.getTransaction(
            id = id,
        )
    }
}
