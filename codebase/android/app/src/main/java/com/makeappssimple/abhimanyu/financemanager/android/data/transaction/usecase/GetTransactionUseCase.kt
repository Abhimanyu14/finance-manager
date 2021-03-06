package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository.TransactionRepository
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction

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
